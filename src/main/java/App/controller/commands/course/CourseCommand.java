package App.controller.commands.course;

import App.controller.commands.Command;
import App.service.CourseService;
import App.service.dto.CourseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.COURSE_PAGE;

@Controller
@RequiredArgsConstructor
public class CourseCommand implements Command {
    private final CourseService service;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        CourseDto course = service.getById(id);
        req.setAttribute("course", course);
        return COURSE_PAGE;
    }
}
