package app.repository;

import app.repository.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRep extends JpaRepository<Course, Long> {

    Optional<Course> findByIdAndDeletedFalse (Long id);

    Page<Course> findByDeletedFalse(Pageable pageable);
}
