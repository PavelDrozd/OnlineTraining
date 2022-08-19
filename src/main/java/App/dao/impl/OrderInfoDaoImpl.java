package App.dao.impl;

import App.dao.CourseDao;
import App.dao.OrderInfoDao;
import App.dao.connection.DataSource;
import App.dao.entity.Course;
import App.dao.entity.OrderInfo;
import App.exceptions.DaoException;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class OrderInfoDaoImpl implements OrderInfoDao {
    public static final String SELECT_ORDERS_INFO = //
            "SELECT i.id, i.course_id, i.order_id, i.course_price ";
    public static final String FROM_ORDER_INFO = //
            "FROM order_info i ";
    public static final String SELECT_FROM_COURSES = "(SELECT id FROM courses WHERE name = ?)";
    public static final String INSERT_ORDER_INFOS = //
            "INSERT INTO order (course_id, order_id, course_quantity, course_price) " //
                    + "VALUES (" + SELECT_FROM_COURSES + ", ?, ?, ?)";
    public static final String SELECT_ALL_ORDERS_INFO_PAGED = SELECT_ORDERS_INFO + FROM_ORDER_INFO//
            + "WHERE i.deleted = false LIMIT ? OFFSET ?";
    public static final String SELECT_ORDER_BY_ID = SELECT_ORDERS_INFO + FROM_ORDER_INFO//
            + "WHERE i.order_id = ? AND i.deleted = false";
    public static final String SELECT_ORDER_INFO_BY_ID = SELECT_ORDERS_INFO + FROM_ORDER_INFO//
            + "WHERE i.id = ? AND i.deleted = false";
    public static final String UPDATE_ORDER_INFO = //
            "UPDATE order_infos SET course_id = ?, order_id = ?, course_price = ? "//
                    + "WHERE id = ? AND i.deleted = false";
    public static final String DELETE_ORDER_INFO = "UPDATE order_infos i SET deleted = true "//
            + "WHERE i.id = ?";
    public static final String COUNT_ORDERS_INFO = "SELECT count(*) AS total " + FROM_ORDER_INFO//
            + "WHERE i.deleted = false";

    private final DataSource dataSource;
    private final CourseDao courseDao;

    public OrderInfoDaoImpl(DataSource dataSource, CourseDao courseDao) {
        this.dataSource = dataSource;
        this.courseDao = courseDao;
    }

    @Override
    public OrderInfo create(OrderInfo orderInfo) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'INSERT' new order info: {}.", orderInfo);
            PreparedStatement statement = connection.prepareStatement(//
                    INSERT_ORDER_INFOS, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, orderInfo.getCourse().getName());
            statement.setLong(2, orderInfo.getOrderId());
            statement.setBigDecimal(3, orderInfo.getCoursePrice());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            OrderInfo created = new OrderInfo();
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
    public List<OrderInfo> getAll(int limit, long offset) {
        List<OrderInfo> ordersInfo = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' (limit) command");
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_INFO_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ordersInfo.add(processOrderInfo(result));
            }
            return ordersInfo;
        } catch (SQLException e) {
            log.error("Error executing the 'getAll' (limit) command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public OrderInfo getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_INFO_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            OrderInfo orderInfo = new OrderInfo();
            if (resultSet.next()) {
                orderInfo = processOrderInfo(resultSet);
            }
            return orderInfo;
        } catch (SQLException e) {
            log.error("Error executing the 'getById' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public OrderInfo update(OrderInfo orderInfos) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' order info parameters to: {}.", orderInfos);
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_INFO);
            statement.setLong(1, orderInfos.getCourse().getId());
            statement.setLong(2, orderInfos.getOrderId());
            statement.setBigDecimal(3, orderInfos.getCoursePrice());
            statement.setLong(4, orderInfos.getId());

            statement.executeUpdate();
            return getById(orderInfos.getId());
        } catch (SQLException e) {
            log.error("Error executing the 'update' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' delete by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_INFO);
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
            ResultSet result = statement.executeQuery(COUNT_ORDERS_INFO);
            if (result.next()) {
                return result.getLong("total");
            }
        } catch (SQLException e) {
            log.error("Error executing the 'count' command throw: ", e);
            throw new DaoException(e);
        }
        throw new DaoException("No elements in return statement 'count'.");
    }

    @Override
    public List<OrderInfo> getByOrderId(Long id) {
        List<OrderInfo> ordersInfo = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ordersInfo.add(processOrderInfo(result));
            }
            return ordersInfo;
        } catch (SQLException e) {
            log.error("Error executing the 'getAll' command throw: ", e);
            throw new DaoException(e);
        }
    }

    private OrderInfo processOrderInfo(ResultSet resultSet) throws SQLException {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(resultSet.getLong("id"));
        Long courseId = resultSet.getLong("course_id");
        Course course = courseDao.getById(courseId);
        orderInfo.setCourse(course);
        orderInfo.setOrderId(resultSet.getLong("order_id"));
        orderInfo.setCoursePrice(resultSet.getBigDecimal("course_price"));
        return orderInfo;
    }

}
