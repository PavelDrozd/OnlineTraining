package App.repository;

import App.repository.entity.User;

import java.util.List;

public interface UserRepository extends AbstractRepository<Long, User> {

    List<User> getByFirstName(String firstName);

    List<User> getByLastName(String lastName);

    User getByEmail(String email);
}
