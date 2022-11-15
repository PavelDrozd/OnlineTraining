package app.web.rest;

import app.exceptions.controller.ValidationException;
import app.service.CourseService;
import app.service.dto.course.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseRestController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public CourseDto get(@PathVariable Long id) {
        return courseService.get(id);
    }

    @GetMapping
    public Page<CourseDto> getAll(Pageable pageable) {
        return courseService.getAll(pageable);
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@RequestBody @Valid CourseDto course, Errors errors) {
        checkErrors(errors);
        CourseDto created = courseService.create(course);
        return buildResponseCourse(created);
    }

    @PutMapping("/{id}")
    public CourseDto update(@PathVariable Long id, @RequestBody @Valid CourseDto course, Errors errors) {
        checkErrors(errors);
        course.setId(id);
        return courseService.update(course);
    }

    @PatchMapping("/{id}")
    public CourseDto updatePart(@PathVariable Long id, @RequestBody @Valid CourseDto course) {
        course.setId(id);
        return courseService.update(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }

    private void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    private ResponseEntity<CourseDto> buildResponseCourse(CourseDto course) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(course))
                .body(course);
    }

    private URI getLocation(CourseDto course) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/courses/{id}")
                .buildAndExpand(course.getId())
                .toUri();
    }
}
