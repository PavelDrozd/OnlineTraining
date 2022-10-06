package App.controller.commands.user.profile;

import App.controller.commands.Command;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.LOGIN_FORM_PAGE;
import static App.controller.commands.PagesConstant.PROFILE_PAGE;

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
