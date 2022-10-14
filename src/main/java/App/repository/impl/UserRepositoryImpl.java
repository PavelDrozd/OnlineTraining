package App.repository.impl;

import App.repository.UserRepository;
import App.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    private static final String FIND_ALL_USERS = "from User where deleted = false";
    private static final String FIND_ALL_USERS_BY_FIRSTNAME = "from User where firstName = :firstName and deleted = false";
    private static final String FIND_ALL_USERS_BY_LASTNAME = "from User where lastName = :lastName and deleted = false";
    private static final String FIND_ALL_USERS_BY_EMAIL = "from User where email = :email and deleted = false";
    private static final String COUNT_USERS = "SELECT count(*) AS total FROM users u WHERE u.deleted = false";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public User create(User user) {
        log.debug("Database query to the 'INSERT' new user: {}.", user);
        manager.persist(user);
        return user;
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        log.debug("Database query to the 'SELECT' users (limit) command");
        return manager.createQuery(FIND_ALL_USERS, User.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @Override
    public User getById(Long id) {
        log.debug("Database query to the 'SELECT' user by id: {}.", id);
        return manager.find(User.class, id);
    }

    @Override
    public User update(User user) {
        log.debug("Database query to the 'UPDATE' user parameters to: {}.", user);
        manager.merge(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete user by id: {}.", id);
        manager.remove(id);
    }

    @Override
    public Integer count() {
        log.debug("Database query to the 'COUNT' all users.");
        return manager.createNativeQuery(COUNT_USERS, Integer.class).getFirstResult();
    }

    @Override
    public List<User> getByFirstName(String firstName) {
        log.debug("Database query to the 'SELECT' by last name: {}.", firstName);
        return manager.createQuery(FIND_ALL_USERS_BY_FIRSTNAME, User.class)//
                .setParameter("firstName", firstName).getResultList();
    }

    @Override
    public List<User> getByLastName(String lastName) {
        log.debug("Database query to the 'SELECT' by last name: {}.", lastName);
        return manager.createQuery(FIND_ALL_USERS_BY_LASTNAME, User.class)//
                .setParameter("lastName", lastName).getResultList();
    }

    @Override
    public User getByEmail(String email) {
        log.debug("Database query to the 'SELECT' by email: {}.", email);
        return manager.createQuery(FIND_ALL_USERS_BY_EMAIL, User.class).setParameter("email", email).getSingleResult();
    }

}
