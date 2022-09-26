package App.dao.impl;

import App.dao.UserDao;
import App.dao.connection.DataSource;
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
public class UserDaoImpl implements UserDao {

    public static final String SELECT_USER = //
            "SELECT u.id, u.firstName, u.lastName, u.age, u.email, u.password, r.name AS role ";
    public static final String FROM_USERS = //
            "FROM users u ";
    public static final String JOIN_ROLES = //
            "JOIN roles r ON u.role_id = r.id ";
    public static final String SELECT_FROM_ROLES = "(SELECT id FROM roles WHERE name = ?)";
    public static final String INSERT_USER = //
            "INSERT INTO users (firstName, lastName, age, email, password, role_id) " //
                    + "VALUES (?, ?, ?, ?, ?, " + SELECT_FROM_ROLES + ")";
    public static final String SELECT_ALL_USERS_PAGED = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.deleted = false LIMIT ? OFFSET ?";
    public static final String SELECT_USER_BY_ID = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.id = ? AND u.deleted = false";
    public static final String SELECT_USERS_BY_FIRSTNAME = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.firstName = ? AND u.deleted = false";
    public static final String SELECT_USERS_BY_LASTNAME = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.lastName = ? AND u.deleted = false";
    public static final String SELECT_USERS_BY_EMAIL = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.email = ? AND u.deleted = false";
    public static final String UPDATE_USER = //
            "UPDATE users SET firstName = ?, lastName = ?, age = ?, email = ?, password = ?, role_id = "//
                    + SELECT_FROM_ROLES//
                    + "WHERE id = ? AND deleted = false";
    public static final String DELETE_USER = "UPDATE users u SET deleted = true "//
            + "WHERE u.id = ?";
    public static final String COUNT_USERS = "SELECT count(*) AS total " + FROM_USERS//
            + "WHERE u.deleted = false";

    private final DataSource dataSource;

    @Override
    public User create(User user) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'INSERT' new user: {}.", user);
            PreparedStatement statement = connection.prepareStatement(//
                    INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole().toString());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            User created = new User();
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
    public List<User> getAll(int limit, long offset) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' (limit) command");
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(processUser(result));
            }
            return users;
        } catch (SQLException e) {
            log.error("Error executing the 'getAll' (limit) command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public User getById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            User user = new User();
            if (result.next()) {
                user = processUser(result);
            }
            return user;
        } catch (SQLException e) {
            log.error("Error executing the 'getById' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public User update(User user) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' user parameters to: {}.", user);
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole().toString());
            statement.setLong(7, user.getId());

            statement.executeUpdate();
            return getById(user.getId());
        } catch (SQLException e) {
            log.error("Error executing the 'update' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'UPDATE' delete by id: {}.", id);
            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
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
            log.debug("Database query to the 'COUNT' all.");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(COUNT_USERS);
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
    public List<User> getByFirstName(String firstName) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by last name: {}.", firstName);
            PreparedStatement statement = connection.prepareStatement(SELECT_USERS_BY_FIRSTNAME);
            statement.setString(1, firstName);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(processUser(result));
            }
            return users;
        } catch (SQLException e) {
            log.error("Error executing the 'getByLastName' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> getByLastName(String lastName) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by last name: {}.", lastName);
            PreparedStatement statement = connection.prepareStatement(SELECT_USERS_BY_LASTNAME);
            statement.setString(1, lastName);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(processUser(result));
            }
            return users;
        } catch (SQLException e) {
            log.error("Error executing the 'getByLastName' command throw: ", e);
            throw new DaoException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("Database query to the 'SELECT' by email: {}.", email);
            PreparedStatement statement = connection.prepareStatement(SELECT_USERS_BY_EMAIL);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            User user = new User();
            if (result.next()) {
                user = processUser(result);
            }
            return user;
        } catch (SQLException e) {
            log.error("Error executing the 'getByEmail' command throw: ", e);
            throw new DaoException(e);
        }
    }

    private User processUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setFirstName(result.getString("firstName"));
        user.setLastName(result.getString("lastName"));
        user.setAge(result.getInt("age"));
        user.setEmail(result.getString("email"));
        user.setPassword(result.getString("password"));
        user.setRole(User.Role.valueOf(result.getString("role")));
        return user;
    }
}
