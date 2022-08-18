package App.controller.commands.user.edit;

import App.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.EDIT_AGE_FORM_PAGE;

public class EditAgeFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return EDIT_AGE_FORM_PAGE;
    }
}
