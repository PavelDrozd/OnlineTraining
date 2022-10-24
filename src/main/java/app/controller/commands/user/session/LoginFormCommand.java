package app.controller.commands.user.session;

import app.controller.commands.Command;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static app.controller.commands.PagesConstant.LOGIN_FORM_PAGE;

@Controller
public class LoginFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return LOGIN_FORM_PAGE;
    }
}
