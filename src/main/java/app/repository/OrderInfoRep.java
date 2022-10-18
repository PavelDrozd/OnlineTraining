package app.repository;

import app.repository.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderInfoRep extends JpaRepository<OrderInfo, Long> {

    Optional<OrderInfo> findByIdAndDeletedFalse (Long id);
}
