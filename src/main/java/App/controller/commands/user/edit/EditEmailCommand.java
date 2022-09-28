package App.controller.commands.user.edit;

import App.controller.commands.Command;
import App.service.UserService;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.PROFILE_PAGE;

@Controller
@RequiredArgsConstructor
public class EditEmailCommand implements Command {
    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        HttpSession session = req.getSession(false);
        UserDto user = (UserDto) session.getAttribute("user");
        user.setEmail(email);
        UserDto edited = userService.update(user);
        req.setAttribute("user", edited);
        req.setAttribute("message", "Email has been changed.");
        return PROFILE_PAGE;
    }
}
