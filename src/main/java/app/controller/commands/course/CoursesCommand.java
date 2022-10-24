package app.controller.commands.course;

import app.controller.commands.Command;
import app.service.CourseService;
import app.service.dto.CourseDto;
import app.service.util.PaginationUtil;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import java.util.List;

import static app.controller.commands.PagesConstant.COURSES_PAGE;

@Controller
@RequiredArgsConstructor
public class CoursesCommand implements Command {
    private final CourseService courseService;
    private final PaginationUtil paginationUtil;

    @Override
    public String execute(HttpServletRequest req) {
        int limit = paginationUtil.getPageSize(req.getParameter("limit"));
        long totalPages = paginationUtil.getTotalPages(limit, courseService.count());
        int page = paginationUtil.getPage(req.getParameter("page"), totalPages);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<CourseDto> courses = courseService.findAll(pageable);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("courses", courses);
        return COURSES_PAGE;
    }
}
