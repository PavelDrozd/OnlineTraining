package App.dao.impl;

import App.dao.UserDao;
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
public class UserDaoImpl implements UserDao {
    private static final String SELECT_USER = //
            "SELECT u.id, u.firstName, u.lastName, u.age, u.email, u.password, r.name AS role ";
    private static final String FROM_USERS = //
            "FROM users u ";
    private static final String JOIN_ROLES = //
            "JOIN roles r ON u.role_id = r.id ";
    private static final String SELECT_FROM_ROLES = "(SELECT id FROM roles WHERE name = ?)";
    private static final String INSERT_USER = //
            "INSERT INTO users (firstName, lastName, age, email, password, role_id) " //
                    + "VALUES (?, ?, ?, ?, ?, " + SELECT_FROM_ROLES + ")";
    private static final String SELECT_ALL_USERS_PAGED = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.deleted = false LIMIT ? OFFSET ?";
    private static final String SELECT_USER_BY_ID = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.id = ? AND u.deleted = false";
    private static final String SELECT_USERS_BY_FIRSTNAME = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.firstName = ? AND u.deleted = false";
    private static final String SELECT_USERS_BY_LASTNAME = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.lastName = ? AND u.deleted = false";
    private static final String SELECT_USERS_BY_EMAIL = SELECT_USER + FROM_USERS + JOIN_ROLES//
            + "WHERE u.email = ? AND u.deleted = false";
    private static final String UPDATE_USER = //
            "UPDATE users SET firstName = ?, lastName = ?, age = ?, email = ?, password = ?, role_id = "//
                    + SELECT_FROM_ROLES//
                    + "WHERE id = ? AND deleted = false";
    private static final String DELETE_USER = "UPDATE users u SET deleted = true "//
            + "WHERE u.id = ?";
    private static final String COUNT_USERS = "SELECT count(*) AS total " + FROM_USERS//
            + "WHERE u.deleted = false";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.debug("Database query to the 'INSERT' new user: {}.", user);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole().toString());
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new DaoException("KeyHolder is null");
        }
        Long id = keyHolder.getKey().longValue();
        return getById(id);
    }

    @Override
    public List<User> getAll(int limit, long offset) {
        log.debug("Database query to the 'SELECT' users (limit) command");
        return jdbcTemplate.query((connection) -> {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_PAGED);
            statement.setInt(1, limit);
            statement.setLong(2, offset);
            return statement;
        }, this::mapRow);
    }

    @Override
    public User getById(Long id) {
        log.debug("Database query to the 'SELECT' user by id: {}.", id);
        Optional<User> user = jdbcTemplate.query(SELECT_USER_BY_ID, this::mapRow, id).stream().findFirst();
        if (user.isEmpty()) {
            throw new DaoException("User is empty");
        }
        return user.get();
    }

    @Override
    public User update(User user) {
        log.debug("Database query to the 'UPDATE' user parameters to: {}.", user);
        jdbcTemplate.update((connection) -> {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getRole().toString());
            statement.setLong(7, user.getId());
            return statement;
        });
        return getById(user.getId());
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete user by id: {}.", id);
        int rowUpdated = jdbcTemplate.update(DELETE_USER, id);
        return rowUpdated == 1;
    }

    @Override
    public Long count() {
        log.debug("Database query to the 'COUNT' all users.");
        return jdbcTemplate.queryForObject(COUNT_USERS, Long.class);
    }

    @Override
    public List<User> getByFirstName(String firstName) {
        log.debug("Database query to the 'SELECT' by last name: {}.", firstName);
        return jdbcTemplate.query(SELECT_USERS_BY_FIRSTNAME, this::mapRow, firstName);
    }

    @Override
    public List<User> getByLastName(String lastName) {
        log.debug("Database query to the 'SELECT' by last name: {}.", lastName);
        return jdbcTemplate.query(SELECT_USERS_BY_LASTNAME, this::mapRow, lastName);
    }

    @Override
    public User getByEmail(String email) {
        log.debug("Database query to the 'SELECT' by email: {}.", email);
        Optional<User> user = jdbcTemplate.query(SELECT_USERS_BY_EMAIL, this::mapRow, email).stream().findFirst();
        if (user.isEmpty()) {
            throw new DaoException("User is empty");
        }
        return user.get();
    }

    private User mapRow(ResultSet result, int num) throws SQLException {
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
