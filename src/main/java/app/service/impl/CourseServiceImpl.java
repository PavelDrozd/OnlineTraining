package app.service.impl;

import app.interceptors.LogInvocation;
import app.repository.CourseRep;
import app.repository.entity.Course;
import app.exceptions.ServiceException;
import app.service.CourseService;
import app.service.dto.CourseDto;
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

    @LogInvocation
    @Override
    public CourseDto create(CourseDto courseDto) {
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isPresent() && existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceException("Course with name " + courseDto.getName() + "already exists.");
        }
        Course course = courseRep.save(toCourseEntity(courseDto));
        return toCourseDto(course);
    }

    @LogInvocation
    @Override
    public List<CourseDto> findAll(Pageable pageable) {
        List<Course> courses = courseRep.findByDeletedFalse(pageable).stream().toList();
        return courses.stream().map(this::toCourseDto).collect(Collectors.toList());
    }

    @LogInvocation
    @Override
    public CourseDto findById(Long id) {
        Optional<Course> course = courseRep.findByIdAndDeletedFalse(id);
        if (course.isEmpty()) {
            throw new ServiceException("Course with id: " + id + "doesn't exist");
        }
        return toCourseDto(course.get());
    }

    @LogInvocation
    @Override
    public CourseDto update(CourseDto courseDto) {
        Optional<Course> existing = courseRep.findById(courseDto.getId());
        if (existing.isPresent() && existing.get().getName().equals(courseDto.getName())) {
            throw new ServiceException("Course with name " + courseDto.getName() + "already exists.");
        }
        Course course = courseRep.save(toCourseEntity(courseDto));
        return toCourseDto(course);
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
