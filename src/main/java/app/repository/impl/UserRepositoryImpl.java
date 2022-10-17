package app.repository.impl;

import app.interceptors.LogInvocation;
import app.repository.UserRepository;
import app.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
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

    @LogInvocation
    @Override
    public User create(User user) {
        manager.persist(user);
        return user;
    }

    @LogInvocation
    @Override
    public List<User> getAll(int limit, int offset) {
        return manager.createQuery(FIND_ALL_USERS, User.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @LogInvocation
    @Override
    public User getById(Long id) {
        return manager.find(User.class, id);
    }

    @LogInvocation
    @Override
    public User update(User user) {
        manager.merge(user);
        return user;
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        manager.remove(id);
    }

    @LogInvocation
    @Override
    public Integer count() {
        return manager.createNativeQuery(COUNT_USERS, Integer.class).getFirstResult();
    }

    @LogInvocation
    @Override
    public List<User> getByFirstName(String firstName) {
        return manager.createQuery(FIND_ALL_USERS_BY_FIRSTNAME, User.class)//
                .setParameter("firstName", firstName).getResultList();
    }

    @LogInvocation
    @Override
    public List<User> getByLastName(String lastName) {
        return manager.createQuery(FIND_ALL_USERS_BY_LASTNAME, User.class)//
                .setParameter("lastName", lastName).getResultList();
    }

    @LogInvocation
    @Override
    public User getByEmail(String email) {
        return manager.createQuery(FIND_ALL_USERS_BY_EMAIL, User.class).setParameter("email", email).getSingleResult();
    }

}
