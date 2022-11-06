package app.repository;

import app.repository.entity.user.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalInfoRep  extends JpaRepository<PersonalInfo, Long> {

    Optional<PersonalInfo> findByEmail(String email);
}
