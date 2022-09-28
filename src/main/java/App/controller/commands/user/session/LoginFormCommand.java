package App.controller.commands.user.session;

import App.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import static App.controller.commands.PagesConstant.LOGIN_FORM_PAGE;

@Controller
public class LoginFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return LOGIN_FORM_PAGE;
    }
}
