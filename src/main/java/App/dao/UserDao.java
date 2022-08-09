package App.dao;

import App.dao.entity.User;

import java.util.List;

public interface UserDao extends AbstractDao<Long, User> {

    List<User> getByFirstName(String firstName);

    List<User> getByLastName(String lastName);

    User getByEmail(String email);
}
