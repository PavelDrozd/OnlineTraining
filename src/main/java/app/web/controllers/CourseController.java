package app.web.controllers;

import app.service.CourseService;
import app.service.dto.course.CourseDto;
import app.service.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final PaginationUtil paginationUtil;

    @GetMapping("/{id}")
    public String showCourse(@PathVariable Long id, Model model) {
        CourseDto course = courseService.findById(id);
        model.addAttribute("course", course);
        return "course/course";
    }

    @GetMapping("/list")
    public String showCourses(String limit, String page, Model model) {
        int pageLimit = paginationUtil.getPageSize(limit);
        long totalPages = paginationUtil.getTotalPages(pageLimit, courseService.count());
        int pageNumber = paginationUtil.getPage(page, totalPages);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber - 1, pageLimit, sort);
        Page<CourseDto> courses = courseService.findAll(pageable);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("courses", courses);
        return "course/courses";
    }

}

