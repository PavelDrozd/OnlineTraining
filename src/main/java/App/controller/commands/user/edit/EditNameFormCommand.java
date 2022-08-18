package App.controller.commands.user.edit;

import App.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.EDIT_NAME_FORM_PAGE;

public class EditNameFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return EDIT_NAME_FORM_PAGE;
    }
}
