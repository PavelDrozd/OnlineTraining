package App.controller.commands.error;

import App.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

import static App.controller.commands.PagesConstant.ERROR_PAGE;


public class ErrorCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return ERROR_PAGE;
    }
}
