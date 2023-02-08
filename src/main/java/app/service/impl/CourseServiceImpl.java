package app.service.impl;

import app.exceptions.service.ServiceNotFoundException;
import app.exceptions.service.ServiceValidationException;
import app.log.Logger;
import app.repository.CourseRep;
import app.repository.entity.course.Course;
import app.service.CourseService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.course.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRep courseRep;
    private final EntityDtoMapper mapper;

    @Logger
    @Override
    public CourseDto create(CourseDto courseDto) {
        checkNull(courseDto);
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isPresent() && existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceNotFoundException("Course with name " + courseDto.getName() + " already exists.");
        }
        Course course = courseRep.save(mapper.mapToCourse(courseDto));
        return mapper.mapToCourseDto(course);
    }

    @Logger
    @Override
    public Page<CourseDto> getAll(Pageable pageable) {
        return courseRep.findAll(pageable).map(mapper::mapToCourseDto);
    }

    @Logger
    @Override
    public CourseDto get(Long id) {
        return courseRep.findById(id)
                .map(mapper::mapToCourseDto)
                .orElseThrow(() -> new ServiceNotFoundException("Course with id: " + id + " doesn't exist"));
    }

    @Logger
    @Override
    public CourseDto update(CourseDto courseDto) {
        checkNull(courseDto);
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isEmpty()) {
            throw new ServiceNotFoundException("Course doesn't exist");
        }
        if (existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceValidationException("Course with name " + courseDto.getName() + " already exists.");
        }
        Course course = courseRep.save(mapper.mapToCourse(courseDto));
        return mapper.mapToCourseDto(course);
    }

    @Logger
    @Override
    public void delete(Long id) {
        Course course = courseRep.findById(id).orElseThrow(() -> new ServiceNotFoundException("Course doesn't exist"));
        course.setDeleted(false);
    }

    @Logger
    @Override
    public Long count() {
        return courseRep.count();
    }

    private void checkNull(CourseDto courseDto) {
        if (courseDto == null) {
            throw new ServiceValidationException("Course is null");
        }
    }

}
