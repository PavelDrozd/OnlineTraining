package App.controller.commands.course;

import App.controller.Command;
import App.controller.util.PagingUtil;
import App.service.CourseService;
import App.service.dto.CourseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static App.controller.commands.PagesConstant.COURSES_PAGE;

public class CoursesCommand implements Command {
    private final CourseService courseService;

    public CoursesCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        PagingUtil.Paging paging = PagingUtil.getPaging(req, courseService.count());
        List<CourseDto> courses = courseService.getAll(paging.getLimit(), paging.getOffset());
        req.setAttribute("currentPage", paging.getPage());
        req.setAttribute("totalPages", paging.getTotalPages());
        req.setAttribute("courses", courses);
        return COURSES_PAGE;
    }
}
