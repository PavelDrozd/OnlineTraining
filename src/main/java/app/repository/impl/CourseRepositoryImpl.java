package app.repository.impl;

import app.interceptors.LogInvocation;
import app.repository.CourseRepository;
import app.repository.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional
public class CourseRepositoryImpl implements CourseRepository {
    private static final String FIND_ALL_COURSES = "from Course where deleted = false";
    private static final String COUNT_COURSES = "SELECT count(*) AS total FROM courses c WHERE c.deleted = false";

    @PersistenceContext
    private EntityManager manager;

    @LogInvocation
    @Override
    public Course create(Course course) {
        manager.persist(course);
        return course;
    }

    @LogInvocation
    @Override
    public List<Course> getAll(int limit, int offset) {
        return manager.createQuery(FIND_ALL_COURSES, Course.class)//
                .setMaxResults(limit)//
                .setFirstResult(offset)//
                .getResultList();
    }

    @LogInvocation
    @Override
    public Course getById(Long id) {
        return manager.find(Course.class, id);
    }

    @LogInvocation
    @Override
    public Course update(Course course) {
        manager.merge(course);
        return course;
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        manager.remove(id);
    }

    @LogInvocation
    @Override
    public Integer count() {
        return manager.createNativeQuery(COUNT_COURSES, Integer.class).getFirstResult();
    }

    @LogInvocation
    @Override
    public Course getByName(String name) {
        return manager.find(Course.class, name);
    }

}
