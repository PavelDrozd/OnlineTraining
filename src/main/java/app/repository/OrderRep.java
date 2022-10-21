package app.repository;

import app.repository.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRep extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndDeletedFalse (Long id);

    Page<Order> findByDeletedFalse(Pageable pageable);
}
