package app.controller.commands.user.edit;

import app.controller.commands.Command;
import app.service.UserService;
import app.service.dto.UserDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.PROFILE_PAGE;

@Controller
@RequiredArgsConstructor
public class EditAgeCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        Integer age = Integer.parseInt(req.getParameter("age"));
        HttpSession session = req.getSession(false);
        UserDto user = (UserDto) session.getAttribute("user");
        user.setAge(age);
        UserDto edited = userService.update(user);
        req.setAttribute("user", edited);
        req.setAttribute("message", "The age has been changed.");
        return PROFILE_PAGE;
    }
}
