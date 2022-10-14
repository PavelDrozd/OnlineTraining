package app.repository;

import app.repository.entity.Course;

public interface CourseRepository extends AbstractRepository<Long, Course> {

    Course getByName(String name);
}
