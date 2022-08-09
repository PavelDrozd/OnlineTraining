package App.controller.commands.user.session;

import App.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.LOGIN_FORM_PAGE;

public class LoginFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return LOGIN_FORM_PAGE;
    }
}
