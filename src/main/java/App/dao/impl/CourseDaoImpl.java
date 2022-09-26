package App.dao.impl;

import App.dao.CourseDao;
import App.dao.connection.DataSource;
import App.dao.entity.Course;
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
public class CourseDaoImpl implements CourseDao {
    public static final String SELECT_COURSE = //
            "SELECT c.id, c.name, c.cost, c.duration_days ";
    public static final String FROM_COURSE = //
            "FROM courses c ";
    public static final String INSERT_COURSE = //
            "INSERT INTO users (name, cost, duration_days) " //
                    + "VALUES (?, ?, ?)";
    public static final String SELECT_ALL_COURSES_PAGED = SELECT_COURSE + FROM_COURSE//
            + "WHERE c.deleted = false LIMIT ? OFFSET ?";
    public static final String SELECT_COURSE_BY_ID = SELECT_COURSE + FROM_COURSE //
            + "WHERE c.id = ? AND c.deleted = false";
    public static final String SELECT_COURSE_BY_NAME = SELECT_COURSE + FROM_COURSE //
            + "WHERE c.name = ? AND c.deleted = false";
    public static final String UPDATE_COURSE = //
            "UPDATE courses SET name = ?, cost = ?, duration_days = ? "//
                    + "WHERE c.id = ? AND c.deleted = false";
    public static final String DELETE_COURSE = "UPDATE courses c SET deleted = true "//
            + "WHERE c.id = ?";
    public static final String COUNT_COURSES = "SELECT count(*) AS total " + FROM_COURSE//
            + "WHERE c.deleted = false";

    private final DataSource dataSource;

    @Override
    public Course create(Course course) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'INSERT' new user: {}.", course);
            PreparedStatement statement = connection.prepareStatement(//
                    INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, course.getName());
            statement.setBigDecimal(2, course.getCost());
            statement.setInt(3, course.getDurationDays());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            Course created = new Course();
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
    public List<Course> getAll(int limit, long offset) {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' (limit) command");
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COURSES_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                courses.add(processCourse(result));
            }
            return courses;
        } catch (SQLException e) {
            log.error("Error executing the 'getAll' (limit) command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Course getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            Course course = new Course();
            if (result.next()) {
                course = processCourse(result);
            }
            return course;
        } catch (SQLException e) {
            log.error("Error executing the 'getById' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Course update(Course course) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' user parameters to: {}.", course);
            PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE);
            statement.setString(1, course.getName());
            statement.setBigDecimal(2, course.getCost());
            statement.setInt(3, course.getDurationDays());
            statement.setLong(4, course.getId());

            statement.executeUpdate();
            return getById(course.getId());
        } catch (SQLException e) {
            log.error("Error executing the 'update' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' delete by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(DELETE_COURSE);
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
            ResultSet result = statement.executeQuery(COUNT_COURSES);
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
    public Course getByName(String name) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by name: {}.", name);
            PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_NAME);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            Course course = new Course();
            if (result.next()) {
                course = processCourse(result);
            }
            return course;
        } catch (SQLException e) {
            log.error("Error executing the 'getById' command throw: ", e);
            throw new DaoException(e);
        }
    }

    private Course processCourse(ResultSet result) throws SQLException {
        Course course = new Course();
        course.setId(result.getLong("id"));
        course.setName(result.getString("name"));
        course.setCost(result.getBigDecimal("cost"));
        course.setDurationDays(result.getInt("duration_days"));
        return course;
    }


}
