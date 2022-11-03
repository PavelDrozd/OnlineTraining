package app.repository;

import app.repository.entity.user.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRep  extends JpaRepository<PersonalInfo, Long> {
}
