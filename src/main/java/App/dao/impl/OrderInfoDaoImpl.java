package App.dao.impl;

import App.dao.CourseDao;
import App.dao.OrderInfoDao;
import App.dao.entity.Course;
import App.dao.entity.OrderInfo;
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
public class OrderInfoDaoImpl implements OrderInfoDao {
    private static final String SELECT_ORDERS_INFO = //
            "SELECT i.id, i.course_id, i.order_id, i.course_price ";
    private static final String FROM_ORDER_INFO = //
            "FROM order_info i ";
    private static final String SELECT_FROM_COURSES = "(SELECT id FROM courses WHERE name = ?)";
    private static final String INSERT_ORDER_INFOS = //
            "INSERT INTO order (course_id, order_id, course_quantity, course_price) " //
                    + "VALUES (" + SELECT_FROM_COURSES + ", ?, ?, ?)";
    private static final String SELECT_ALL_ORDERS_INFO_PAGED = SELECT_ORDERS_INFO + FROM_ORDER_INFO//
            + "WHERE i.deleted = false LIMIT ? OFFSET ?";
    private static final String SELECT_ORDER_BY_ID = SELECT_ORDERS_INFO + FROM_ORDER_INFO//
            + "WHERE i.order_id = ? AND i.deleted = false";
    private static final String SELECT_ORDER_INFO_BY_ID = SELECT_ORDERS_INFO + FROM_ORDER_INFO//
            + "WHERE i.id = ? AND i.deleted = false";
    private static final String UPDATE_ORDER_INFO = //
            "UPDATE order_infos SET course_id = ?, order_id = ?, course_price = ? "//
                    + "WHERE id = ? AND i.deleted = false";
    private static final String DELETE_ORDER_INFO = "UPDATE order_infos i SET deleted = true "//
            + "WHERE i.id = ?";
    private static final String COUNT_ORDERS_INFO = "SELECT count(*) AS total " + FROM_ORDER_INFO//
            + "WHERE i.deleted = false";

    private final JdbcTemplate jdbcTemplate;
    private final CourseDao courseDao;

    @Override
    public OrderInfo create(OrderInfo orderInfo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.debug("Database query to the 'INSERT' new order info: {}.", orderInfo);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_INFOS, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, orderInfo.getCourse().getName());
            statement.setLong(2, orderInfo.getOrderId());
            statement.setBigDecimal(3, orderInfo.getCoursePrice());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new DaoException("KeyHolder is null");
        }
        Long id = keyHolder.getKey().longValue();
        return getById(id);
    }

    @Override
    public List<OrderInfo> getAll(int limit, long offset) {
        log.debug("Database query to the 'SELECT' order infos (limit) command");
        return jdbcTemplate.query((connection) -> {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_INFO_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            return statement;
        }, this::mapRow);
    }

    @Override
    public OrderInfo getById(Long id) {
        log.debug("Database query to the 'SELECT' order info by id: {}.", id);
        Optional<OrderInfo> order = jdbcTemplate.query(SELECT_ORDER_INFO_BY_ID, this::mapRow, id).stream().findFirst();
        if (order.isEmpty()) {
            throw new DaoException("Order info is empty");
        }
        return order.get();
    }

    @Override
    public OrderInfo update(OrderInfo orderInfos) {
        log.debug("Database query to the 'UPDATE' order info parameters to: {}.", orderInfos);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_INFO);
            statement.setLong(1, orderInfos.getCourse().getId());
            statement.setLong(2, orderInfos.getOrderId());
            statement.setBigDecimal(3, orderInfos.getCoursePrice());
            statement.setLong(4, orderInfos.getId());
            return statement;
        });
        return getById(orderInfos.getId());
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete order info by id: {}.", id);
        int rowUpdated = jdbcTemplate.update(DELETE_ORDER_INFO, id);
        return rowUpdated == 1;
    }

    @Override
    public Long count() {
        log.debug("Database query to the 'COUNT' all order infos.");
        return jdbcTemplate.queryForObject(COUNT_ORDERS_INFO, Long.class);
    }

    @Override
    public List<OrderInfo> getByOrderId(Long id) {
        log.debug("Database query to the 'SELECT' order_infos by order id: {}.", id);
        return jdbcTemplate.query(SELECT_ORDER_BY_ID, this::mapRow, id);
    }

    private OrderInfo mapRow(ResultSet resultSet, int num) throws SQLException {
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
