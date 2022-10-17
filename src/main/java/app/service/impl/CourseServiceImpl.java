package app.service.impl;

import app.interceptors.LogInvocation;
import app.repository.CourseRepository;
import app.repository.entity.Course;
import app.exceptions.DaoException;
import app.exceptions.ServiceException;
import app.service.CourseService;
import app.service.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @LogInvocation
    @Override
    public CourseDto create(CourseDto courseDto) {
        try {
            Course existing = courseRepository.getByName(courseDto.getName());
            if (existing != null) {
                throw new RuntimeException("Course with name " + courseDto.getName() + " already exists.");
            }
            Course course = courseRepository.create(toCourseEntity(courseDto));
            return toCourseDto(course);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public List<CourseDto> getAll(int limit, int offset) {
        try {
            return courseRepository.getAll(limit, offset).stream().map(this::toCourseDto).collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public CourseDto getById(Long id) {
        try {
            if (courseRepository.getById(id) == null) {
                return null;
            }
            return toCourseDto(courseRepository.getById(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public CourseDto update(CourseDto courseDto) {
        try {
            Course existing = courseRepository.getByName(courseDto.getName());
            if (existing != null && !existing.getName().equals(courseDto.getName())) {
                throw new RuntimeException("Course with name " + courseDto.getName() + "already exists.");
            }
            Course course = courseRepository.update(toCourseEntity(courseDto));
            return toCourseDto(course);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        courseRepository.delete(id);
    }

    @LogInvocation
    @Override
    public Integer count() {
        try {
            return courseRepository.count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @LogInvocation
    @Override
    public CourseDto getByName(String name) {
        try {
            if (courseRepository.getByName(name) == null) {
                return null;
            }
            return toCourseDto(courseRepository.getByName(name));
        } catch (DaoException e) {
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
