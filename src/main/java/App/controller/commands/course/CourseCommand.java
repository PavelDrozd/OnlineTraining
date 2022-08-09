package App.controller.commands.course;

import App.controller.Command;
import App.service.CourseService;
import App.service.dto.CourseDto;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.COURSE_PAGE;

public class CourseCommand implements Command {
    private final CourseService service;

    public CourseCommand(CourseService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        CourseDto course = service.getById(id);
        req.setAttribute("course", course);
        return COURSE_PAGE;
    }
}
