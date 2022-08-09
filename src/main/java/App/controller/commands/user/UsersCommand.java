package App.controller.commands.user;

import App.controller.Command;
import App.controller.util.PagingUtil;
import App.service.UserService;
import App.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static App.controller.commands.PagesConstant.USERS_PAGE;

public class UsersCommand implements Command {
    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest req) {
        PagingUtil.Paging paging = PagingUtil.getPaging(req, userService.count());
        List<UserDto> users = userService.getAll(paging.getLimit(), paging.getOffset());
        req.setAttribute("currentPage", paging.getPage());
        req.setAttribute("totalPages", paging.getTotalPages());
        req.setAttribute("users", users);
        return USERS_PAGE;
    }
}
