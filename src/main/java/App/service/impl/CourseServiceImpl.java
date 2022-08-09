package App.service.impl;

import App.dao.CourseDao;
import App.dao.entity.Course;
import App.service.CourseService;
import App.service.dto.CourseDto;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;

    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public CourseDto create(CourseDto courseDto) {
        log.debug("Service 'create' new courseDto: {}.", courseDto);
        Course existing = courseDao.getByName(courseDto.getName());
        if (existing != null) {
            throw new RuntimeException("Course with name " + courseDto.getName() + " already exists.");
        }
        Course course = courseDao.create(toCourseEntity(courseDto));
        return toCourseDto(course);
    }

    @Override
    public List<CourseDto> getAll(int limit, long offset) {
        log.debug("Service 'getAll' command request.");
        return courseDao.getAll(limit, offset).stream().map(this::toCourseDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto getById(Long id) {
        log.debug("Service 'getById' id: {}.", id);
        if (courseDao.getById(id) == null) {
            return null;
        }
        return toCourseDto(courseDao.getById(id));
    }

    @Override
    public CourseDto update(CourseDto courseDto) {
        log.debug("Service 'update' userDto: {}.", courseDto);
        Course existing = courseDao.getByName(courseDto.getName());
        if (existing != null && !existing.getName().equals(courseDto.getName())) {
            throw new RuntimeException("Course with name " + courseDto.getName() + "already exists.");
        }
        Course course = courseDao.update(toCourseEntity(courseDto));
        return toCourseDto(course);
    }

    @Override
    public void delete(Long id) {
        log.debug("Service 'delete' by id: {}.", id);
        if (courseDao.delete(id)) {
            throw new RuntimeException("Can't delete course by id: " + id);
        }
    }

    @Override
    public Long count() {
        return courseDao.count();
    }

    @Override
    public CourseDto getByName(String name) {
        log.debug("Service 'getByName' name: {}.", name);
        if (courseDao.getByName(name) == null) {
            return null;
        }
        return toCourseDto(courseDao.getByName(name));
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
