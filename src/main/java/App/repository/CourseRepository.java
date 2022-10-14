package App.repository;

import App.repository.entity.Course;

public interface CourseRepository extends AbstractRepository<Long, Course> {

    Course getByName(String name);
}
