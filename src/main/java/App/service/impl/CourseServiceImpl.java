package App.service.impl;

import App.dao.CourseDao;
import App.dao.entity.Course;
import App.exceptions.DaoException;
import App.exceptions.ServiceException;
import App.service.CourseService;
import App.service.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;

    @Override
    public CourseDto create(CourseDto courseDto) {
        try {
            log.debug("Service 'create' new courseDto: {}.", courseDto);
            Course existing = courseDao.getByName(courseDto.getName());
            if (existing != null) {
                throw new RuntimeException("Course with name " + courseDto.getName() + " already exists.");
            }
            Course course = courseDao.create(toCourseEntity(courseDto));
            return toCourseDto(course);
        } catch (DaoException e) {
            log.error("Service can't create 'course': {} , throw: {}", courseDto, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<CourseDto> getAll(int limit, long offset) {
        try {
            log.debug("Service 'getAll' command request.");
            return courseDao.getAll(limit, offset).stream().map(this::toCourseDto).collect(Collectors.toList());
        } catch (DaoException e) {
            log.error("Service can't get all 'courses', throw: {}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDto getById(Long id) {
        try {
            log.debug("Service 'getById' id: {}.", id);
            if (courseDao.getById(id) == null) {
                return null;
            }
            return toCourseDto(courseDao.getById(id));
        } catch (DaoException e) {
            log.error("Service can't get 'course' by id: {} , throw: {}", id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDto update(CourseDto courseDto) {
        try {
            log.debug("Service 'update' userDto: {}.", courseDto);
            Course existing = courseDao.getByName(courseDto.getName());
            if (existing != null && !existing.getName().equals(courseDto.getName())) {
                throw new RuntimeException("Course with name " + courseDto.getName() + "already exists.");
            }
            Course course = courseDao.update(toCourseEntity(courseDto));
            return toCourseDto(course);
        } catch (DaoException e) {
            log.error("Service can't update 'course' to: {} , throw: {}", courseDto, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.debug("Service 'delete' by id: {}.", id);
            if (courseDao.delete(id)) {
                throw new RuntimeException("Can't delete course by id: " + id);
            }
        } catch (DaoException e) {
            log.error("Service can't delete 'course' by id: {} , throw: {}", id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Long count() {
        try {
            return courseDao.count();
        } catch (DaoException e) {
            log.error("Service can't count 'courses', throw: {}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public CourseDto getByName(String name) {
        try {
            log.debug("Service 'getByName' name: {}.", name);
            if (courseDao.getByName(name) == null) {
                return null;
            }
            return toCourseDto(courseDao.getByName(name));
        } catch (DaoException e) {
            log.error("Service can't get 'course' by name: {} , throw: {}", name, e);
            throw new ServiceException(e);
        }
    }

    private Course toCourseEntity(CourseDto courseDto) {
        Course course = new Course();
        if (course.getId() != null) {
            course.setId(courseDto.getId());
        }
        course.setName(courseDto.getName());
        course.setCost(courseDto.getCost());
        course.setDurationDays(courseDto.getDurationDays());
        return course;
    }

    private CourseDto toCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setCost(course.getCost());
        courseDto.setDurationDays(course.getDurationDays());
        return courseDto;
    }


}
