package App.dao.impl;

import App.dao.OrderDao;
import App.dao.OrderInfoDao;
import App.dao.UserDao;
import App.dao.entity.Order;
import App.dao.entity.OrderInfo;
import App.dao.entity.User;
import App.exceptions.DaoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Repository
public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_ORDER = //
            "SELECT o.id, o.user_id, s.name AS status, o.total_cost ";
    private static final String FROM_ORDER = //
            "FROM orders o ";
    private static final String JOIN_STATUS = //
            "JOIN order_status s ON o.status_id = s.id ";
    private static final String INSERT_ORDER = //
            "INSERT INTO order (user_id, status_id, total_cost) " //
                    + "VALUES (?, ?, ?)";
    private static final String SELECT_ALL_ORDERS_PAGED = SELECT_ORDER + FROM_ORDER + JOIN_STATUS//
            + "WHERE o.deleted = false LIMIT ? OFFSET ?";
    private static final String SELECT_ORDER_BY_ID = SELECT_ORDER + FROM_ORDER + JOIN_STATUS //
            + "WHERE o.id = ? AND o.deleted = false";
    private static final String UPDATE_ORDER = //
            "UPDATE courses SET user_id = ?, status_id = ?, total_cost = ?"//
                    + "WHERE id = ? AND o.deleted = false";
    private static final String DELETE_ORDER = "UPDATE courses c SET deleted = true "//
            + "WHERE o.id = ?";
    private static final String COUNT_ORDERS = "SELECT count(*) AS total " + FROM_ORDER//
            + "WHERE o.deleted = false";

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;
    private final OrderInfoDao orderInfoDao;

    @Override
    public Order create(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.debug("Database query to the 'INSERT' new order: {}.", order);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUser().getId());
            statement.setString(2, order.getStatus().toString());
            statement.setBigDecimal(3, order.getTotalCost());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new DaoException("KeyHolder is null");
        }
        Long id = keyHolder.getKey().longValue();
        return getById(id);
    }

    @Override
    public List<Order> getAll(int limit, long offset) {
        log.debug("Database query to the 'SELECT' orders (limit) command");
        return jdbcTemplate.query((connection) -> {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            return statement;
        }, this::mapRow);
    }

    @Override
    public Order getById(Long id) {
        log.debug("Database query to the 'SELECT' order by id: {}.", id);
        Optional<Order> order = jdbcTemplate.query(SELECT_ORDER_BY_ID, this::mapRow, id).stream().findFirst();
        if (order.isEmpty()) {
            throw new DaoException("Order is empty");
        }
        return order.get();
    }

    @Override
    public Order update(Order order) {
        log.debug("Database query to the 'UPDATE' order parameters to: {}.", order);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setLong(1, order.getUser().getId());
            statement.setString(2, order.getStatus().toString());
            statement.setBigDecimal(3, order.getTotalCost());
            statement.setLong(4, order.getId());
            return statement;
        });
        return getById(order.getId());
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete order by id: {}.", id);
        int rowUpdated = jdbcTemplate.update(DELETE_ORDER, id);
        return rowUpdated == 1;
    }

    @Override
    public Long count() {
        log.debug("Database query to the 'COUNT' all orders.");
        return jdbcTemplate.queryForObject(COUNT_ORDERS, Long.class);
    }

    private Order mapRow(ResultSet resultSet, int row) throws SQLException {
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
