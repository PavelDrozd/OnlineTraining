package App.controller.commands.user.session;

import App.controller.Command;
import App.service.UserService;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static App.controller.commands.PagesConstant.INDEX_PAGE;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDto user = userService.login(email, password);
        HttpSession  session = req.getSession();
        session.setAttribute("user", user);
        req.setAttribute("message", "Succesfully login!");
        return INDEX_PAGE;
    }
}
