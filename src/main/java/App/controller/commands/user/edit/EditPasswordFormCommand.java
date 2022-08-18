package App.controller.commands.user.edit;

import App.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.EDIT_PASSWORD_FORM_PAGE;

public class EditPasswordFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return EDIT_PASSWORD_FORM_PAGE;
    }
}
