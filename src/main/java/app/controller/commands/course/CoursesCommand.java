package app.controller.commands.course;

import app.controller.commands.Command;
import app.controller.util.PagingUtil;
import app.service.CourseService;
import app.service.dto.CourseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

import static app.controller.commands.PagesConstant.COURSES_PAGE;

@Controller
@RequiredArgsConstructor
public class CoursesCommand implements Command {
    private final CourseService courseService;

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
