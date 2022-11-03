package app.service.impl;

import app.exceptions.ServiceException;
import app.interceptors.LogInvocation;
import app.repository.CourseRep;
import app.repository.entity.course.Course;
import app.service.CourseService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.course.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRep courseRep;
    private final EntityDtoMapper mapper;

    @LogInvocation
    @Override
    public CourseDto create(CourseDto courseDto) {
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isPresent() && existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceException("Course with name " + courseDto.getName() + "already exists.");
        }
        Course course = courseRep.save(mapper.mapToCourse(courseDto));
        return mapper.mapToCourseDto(course);
    }

    @LogInvocation
    @Override
    public List<CourseDto> findAll(Pageable pageable) {
        List<Course> courses = courseRep.findAll(pageable).stream().toList();
        return courses.stream().map(mapper::mapToCourseDto).collect(Collectors.toList());
    }

    @LogInvocation
    @Override
    public CourseDto findById(Long id) {
        Optional<Course> course = courseRep.findById(id);
        if (course.isEmpty()) {
            throw new ServiceException("Course with id: " + id + "doesn't exist");
        }
        return mapper.mapToCourseDto(course.get());
    }

    @LogInvocation
    @Override
    public CourseDto update(CourseDto courseDto) {
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isPresent() && existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceException("Course with name " + courseDto.getName() + "already exists.");
        }
        Course course = courseRep.save(mapper.mapToCourse(courseDto));
        return mapper.mapToCourseDto(course);
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        Course course = courseRep.findById(id).orElseThrow(() -> new ServiceException("Course doesn't exist"));
        course.setDeleted(false);
    }

    @LogInvocation
    @Override
    public Long count() {
        return courseRep.count();
    }

}
