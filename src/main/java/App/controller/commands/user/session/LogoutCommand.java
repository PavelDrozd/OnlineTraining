package App.controller.commands.user.session;

import App.controller.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static App.controller.commands.PagesConstant.INDEX_PAGE;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = (String) session.getAttribute("lang");
        session.invalidate();
        req.getSession().setAttribute("lang", lang);
        return INDEX_PAGE;

    }
}
