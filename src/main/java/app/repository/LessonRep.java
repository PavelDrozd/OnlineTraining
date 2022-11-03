package app.repository;

import app.repository.entity.course.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRep extends JpaRepository<Lesson, Long> {
}
