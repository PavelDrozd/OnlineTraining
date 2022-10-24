package app.controller.commands.user.profile;

import app.controller.commands.Command;
import app.service.dto.UserDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.LOGIN_FORM_PAGE;
import static app.controller.commands.PagesConstant.PROFILE_PAGE;

@Controller
public class ProfileCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null) {
            req.setAttribute("message", "Please login.");
            return LOGIN_FORM_PAGE;
        }
        return PROFILE_PAGE;
    }
}
