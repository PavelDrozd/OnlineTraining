package App.dao;

import App.dao.entity.Course;

public interface CourseDao extends AbstractDao<Long, Course> {

    Course getByName(String name);
}
