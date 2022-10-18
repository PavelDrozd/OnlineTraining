package app.repository;

import app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRep extends JpaRepository<User, Long> {

    Optional<User> findByIdAndDeletedFalse (Long id);

    Page<User> findByDeletedFalse(Pageable pageable);

    User getByEmail(String email);
}
