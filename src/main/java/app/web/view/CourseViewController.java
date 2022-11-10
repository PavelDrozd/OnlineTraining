package app.web.view;

import app.service.CourseService;
import app.service.dto.course.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseViewController {

    private static final String ATTR_COURSE = "course";

    private static final String COURSE_PAGE = "course/course";
    private static final String COURSES_PAGE = "course/courses";
    private static final String CREATE_FORM_PAGE = "course/create";
    private static final String EDIT_FORM_PAGE = "course/edit";

    private final CourseService courseService;

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        CourseDto course = courseService.get(id);
        model.addAttribute(ATTR_COURSE, course);
        return COURSE_PAGE;
    }

    @GetMapping
    public String getAll() {
        return COURSES_PAGE;
    }

    @GetMapping("/create")
    public String create() {
        return CREATE_FORM_PAGE;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        CourseDto course = courseService.get(id);
        model.addAttribute(ATTR_COURSE, course);
        return EDIT_FORM_PAGE;
    }

}
