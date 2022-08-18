package App.controller.commands.user.edit;

import App.controller.Command;
import App.service.UserService;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static App.controller.commands.PagesConstant.PROFILE_PAGE;

public class EditPasswordCommand implements Command {
    private final UserService userService;

    public EditPasswordCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        user.setPassword(password);
        UserDto edited = userService.update(user);
        req.setAttribute("user", edited);
        req.setAttribute("message", "User name has been changed.");
        return PROFILE_PAGE;
    }
}
