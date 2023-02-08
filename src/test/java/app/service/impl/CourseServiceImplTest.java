package app.service.impl;

import app.exceptions.service.ServiceNotFoundException;
import app.exceptions.service.ServiceValidationException;
import app.repository.CourseRep;
import app.repository.entity.course.Course;
import app.service.CourseService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.course.CourseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseServiceImplTest {
    private static CourseService courseService;

    private static final Long INVALID_COURSE_ID = Long.MIN_VALUE;

    @BeforeAll
    static void beforeAll() {
        CourseRep courseRep = Mockito.mock(CourseRep.class);

        Course course = new Course();
        course.setId(1L);
        course.setCourseInfo("Test info");
        course.setCost(BigDecimal.ONE);
        course.setName("Test name");
        course.setDurationDays(1);
        course.setDiscount(BigDecimal.ZERO);
        course.setDeleted(false);

        Mockito.when(courseRep.save(course)).thenReturn(course);
        Mockito.when(courseRep.findById(1L)).thenReturn(Optional.of(course));

        EntityDtoMapper mapper = new EntityDtoMapper(new ModelMapper());
        courseService = new CourseServiceImpl(courseRep, mapper);
    }

    @Test
    void createCourseNullInExceptionOut() {
        assertThrows(ServiceValidationException.class, () -> courseService.create(null));
    }

    @Test
    void createCourseWithInvalidID() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(INVALID_COURSE_ID);
        assertThrows(ServiceNotFoundException.class, () -> courseService.update(courseDto));
    }

    @Test
    void getCourseNotFound() {
        assertThrows(ServiceNotFoundException.class, () -> courseService.get(INVALID_COURSE_ID));
    }

    @Test
    void updateCourseWithInvalidID() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(INVALID_COURSE_ID);
        assertThrows(ServiceNotFoundException.class, () -> courseService.update(courseDto));
    }

    @Test
    void updateCourseNullInExceptionOut() {
        assertThrows(ServiceValidationException.class, () -> courseService.update(null));
    }

    @Test
    void deleteCourseWithInvalidID() {
        assertThrows(ServiceNotFoundException.class, () -> courseService.get(INVALID_COURSE_ID));
    }

}