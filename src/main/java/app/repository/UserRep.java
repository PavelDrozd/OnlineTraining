package app.repository;

import app.repository.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRep extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String login);
}
