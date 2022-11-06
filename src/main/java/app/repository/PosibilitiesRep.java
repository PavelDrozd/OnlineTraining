package app.repository;

import app.repository.entity.user.Possibilities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosibilitiesRep extends JpaRepository<Possibilities, Long> {
}
