package App.controller.commands.user;

import App.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.CREATE_USER_FORM_PAGE;

public class CreateUserFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return CREATE_USER_FORM_PAGE;
    }
}
