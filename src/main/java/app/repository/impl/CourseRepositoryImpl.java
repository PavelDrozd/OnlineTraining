package app.repository.impl;

import app.repository.CourseRepository;
import app.repository.entity.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Repository
@Transactional
public class CourseRepositoryImpl implements CourseRepository {
    private static final String FIND_ALL_COURSES = "from Course where deleted = false";
    private static final String COUNT_COURSES = "SELECT count(*) AS total FROM courses c WHERE c.deleted = false";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Course create(Course course) {
        log.debug("Database query to the 'INSERT' new course: {}.", course);
        manager.persist(course);
        return course;
    }

    @Override
    public List<Course> getAll(int limit, int offset) {
        log.debug("Database query to the 'SELECT' courses command (limit: {}), (offset: {})");
        return manager.createQuery(FIND_ALL_COURSES, Course.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @Override
    public Course getById(Long id) {
        log.debug("Database query to the 'SELECT' course by id: {}.", id);
        return manager.find(Course.class, id);
    }

    @Override
    public Course update(Course course) {
        log.debug("Database query to the 'UPDATE' course parameters to: {}.", course);
        manager.merge(course);
        return course;
    }

    @Override
    public void delete(Long id) {
        log.debug("Database query to the 'UPDATE' delete course by id: {}.", id);
        manager.remove(id);
    }

    @Override
    public Integer count() {
        log.debug("Database query to the 'COUNT' all courses.");
        return manager.createNativeQuery(COUNT_COURSES, Integer.class).getFirstResult();
    }

    @Override
    public Course getByName(String name) {
        log.debug("Database query to the 'SELECT' by name: {}.", name);
        return manager.find(Course.class, name);
    }

}
