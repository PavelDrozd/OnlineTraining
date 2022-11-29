package app.service.impl;

import app.exceptions.service.ServiceException;
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
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isPresent() && existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceException("Course with name " + courseDto.getName() + " already exists.");
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
                .orElseThrow(() -> new ServiceException("Course with id: " + id + " doesn't exist"));
    }

    @Logger
    @Override
    public CourseDto update(CourseDto courseDto) {
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isPresent() && existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceException("Course with name " + courseDto.getName() + " already exists.");
        }
        Course course = courseRep.save(mapper.mapToCourse(courseDto));
        return mapper.mapToCourseDto(course);
    }

    @Logger
    @Override
    public void delete(Long id) {
        Course course = courseRep.findById(id).orElseThrow(() -> new ServiceException("Course doesn't exist"));
        course.setDeleted(false);
    }

    @Logger
    @Override
    public Long count() {
        return courseRep.count();
    }

}
