package app.repository;

import app.repository.entity.course.CourseProgression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseProgressionRep extends JpaRepository<CourseProgression, Long> {
}
