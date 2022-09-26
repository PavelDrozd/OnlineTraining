package App.dao.impl;

import App.dao.OrderDao;
import App.dao.OrderInfoDao;
import App.dao.UserDao;
import App.dao.connection.DataSource;
import App.dao.entity.Order;
import App.dao.entity.OrderInfo;
import App.dao.entity.User;
import App.exceptions.DaoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Repository
public class OrderDaoImpl implements OrderDao {
    public static final String SELECT_ORDER = //
            "SELECT o.id, o.user_id, s.name AS status, o.total_cost ";
    public static final String FROM_ORDER = //
            "FROM orders o ";
    public static final String JOIN_STATUS = //
            "JOIN order_status s ON o.status_id = s.id ";
    public static final String INSERT_ORDER = //
            "INSERT INTO order (user_id, status_id, total_cost) " //
                    + "VALUES (?, ?, ?)";
    public static final String SELECT_ALL_ORDERS_PAGED = SELECT_ORDER + FROM_ORDER + JOIN_STATUS//
            + "WHERE o.deleted = false LIMIT ? OFFSET ?";
    public static final String SELECT_ORDER_BY_ID = SELECT_ORDER + FROM_ORDER + JOIN_STATUS //
            + "WHERE o.id = ? AND o.deleted = false";
    public static final String UPDATE_ORDER = //
            "UPDATE courses SET user_id = ?, status_id = ?, total_cost = ?, order_date = ? "//
                    + "WHERE id = ? AND o.deleted = false";
    public static final String DELETE_ORDER = "UPDATE courses c SET deleted = true "//
            + "WHERE o.id = ?";
    public static final String COUNT_ORDERS = "SELECT count(*) AS total " + FROM_ORDER//
            + "WHERE o.deleted = false";

    private final DataSource dataSource;
    private final UserDao userDao;
    private final OrderInfoDao orderInfoDao;

    @Override
    public Order create(Order order) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'INSERT' new order: {}.", order);
            PreparedStatement statement = connection.prepareStatement(//
                    INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUser().getId());
            statement.setString(2, order.getStatus().toString());
            statement.setBigDecimal(3, order.getTotalCost());
            statement.executeUpdate();

            ResultSet key = statement.getGeneratedKeys();
            Order created = new Order();
            if (key.next()) {
                created = getById(key.getLong("id"));
            }
            return created;
        } catch (SQLException e) {
            log.error("Error executing the 'create' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> getAll(int limit, long offset) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' (limit) command");
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                orders.add(processOrder(result));
            }
            return orders;
        } catch (SQLException e) {
            log.error("Error executing the 'getAll' (limit) command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Order getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Order order = new Order();
            if (result.next()) {
                order = processOrder(result);
            }
            return order;
        } catch (SQLException e) {
            log.error("Error executing the 'getById' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Order update(Order order) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' order parameters to: {}.", order);
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setLong(1, order.getUser().getId());
            statement.setString(2, order.getStatus().toString());
            statement.setBigDecimal(3, order.getTotalCost());
            statement.setLong(6, order.getId());

            statement.executeUpdate();
            return getById(order.getId());
        } catch (SQLException e) {
            log.error("Error executing the 'update' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' delete by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(DELETE_ORDER);
            statement.setLong(1, id);
            int rowDeleted = statement.executeUpdate();
            return rowDeleted == 1;
        } catch (SQLException e) {
            log.error("Error executing the 'delete' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Long count() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(COUNT_ORDERS);
            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error("Error executing the 'count' command throw: ", e);
            throw new DaoException(e);
        }
        throw new DaoException("No elements in return statement 'count'.");
    }

    private Order processOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        Long userId = resultSet.getLong("user_id");
        User user = userDao.getById(userId);
        order.setUser(user);
        order.setStatus(Order.Status.valueOf(resultSet.getString("status")));
        order.setTotalCost(resultSet.getBigDecimal("total_cost"));
        List<OrderInfo> details = orderInfoDao.getByOrderId(order.getId());
        order.setDetails(details);
        return order;
    }
}
