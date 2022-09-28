package App.dao.impl;

import App.dao.CourseDao;
import App.dao.entity.Course;
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
public class CourseDaoImpl implements CourseDao {
    private static final String SELECT_COURSE = //
            "SELECT c.id, c.name, c.cost, c.duration_days ";
    private static final String FROM_COURSE = //
            "FROM courses c ";
    private static final String INSERT_COURSE = //
            "INSERT INTO users (name, cost, duration_days) " //
                    + "VALUES (?, ?, ?)";
    private static final String SELECT_ALL_COURSES_PAGED = SELECT_COURSE + FROM_COURSE//
            + "WHERE c.deleted = false LIMIT ? OFFSET ?";
    private static final String SELECT_COURSE_BY_ID = SELECT_COURSE + FROM_COURSE //
            + "WHERE c.id = ? AND c.deleted = false";
    private static final String SELECT_COURSE_BY_NAME = SELECT_COURSE + FROM_COURSE //
            + "WHERE c.name = ? AND c.deleted = false";
    private static final String UPDATE_COURSE = //
            "UPDATE courses SET name = ?, cost = ?, duration_days = ? "//
                    + "WHERE c.id = ? AND c.deleted = false";
    private static final String DELETE_COURSE = "UPDATE courses c SET deleted = true "//
            + "WHERE c.id = ?";
    private static final String COUNT_COURSES = "SELECT count(*) AS total " + FROM_COURSE//
            + "WHERE c.deleted = false";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Course create(Course course) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.debug("Database query to the 'INSERT' new course: {}.", course);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, course.getName());
            statement.setBigDecimal(2, course.getCost());
            statement.setInt(3, course.getDurationDays());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new DaoException("KeyHolder is null");
        }
        Long id = keyHolder.getKey().longValue();
        return getById(id);
    }

    @Override
    public List<Course> getAll(int limit, long offset) {
        log.debug("Database query to the 'SELECT' courses (limit) command");
        return jdbcTemplate.query((connection) -> {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COURSES_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            return statement;
        }, this::mapRow);
    }

    @Override
    public Course getById(Long id) {
        log.debug("Database query to the 'SELECT' course by id: {}.", id);
        Optional<Course> course = jdbcTemplate.query(SELECT_COURSE_BY_ID, this::mapRow, id).stream().findFirst();
        if (course.isEmpty()) {
            throw new DaoException("Course is empty");
        }
        return course.get();
    }

    @Override
    public Course update(Course course) {
        log.debug("Database query to the 'UPDATE' course parameters to: {}.", course);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE);
            statement.setString(1, course.getName());
            statement.setBigDecimal(2, course.getCost());
            statement.setInt(3, course.getDurationDays());
            statement.setLong(4, course.getId());
            return statement;
        });
        return getById(course.getId());
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete course by id: {}.", id);
        int rowUpdated = jdbcTemplate.update(DELETE_COURSE, id);
        return rowUpdated == 1;
    }

    @Override
    public Long count() {
        log.debug("Database query to the 'COUNT' all courses.");
        return jdbcTemplate.queryForObject(COUNT_COURSES, Long.class);
    }

    @Override
    public Course getByName(String name) {
        log.debug("Database query to the 'SELECT' by name: {}.", name);
        Optional<Course> course = jdbcTemplate.query(SELECT_COURSE_BY_NAME, this::mapRow).stream().findFirst();
        if (course.isEmpty()) {
            throw new DaoException("Course is empty");
        }
        return course.get();
    }

    private Course mapRow(ResultSet result, int num) throws SQLException {
        Course course = new Course();
        course.setId(result.getLong("id"));
        course.setName(result.getString("name"));
        course.setCost(result.getBigDecimal("cost"));
        course.setDurationDays(result.getInt("duration_days"));
        return course;
    }


}
