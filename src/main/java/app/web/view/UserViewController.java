package app.web.view;

import app.service.UserService;
import app.service.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserViewController {

    private final String ATTR_LANGUAGE ="lang";
    private final String ATTR_USER ="user";
    private final String ATTR_MESSAGE ="user";
    private final String MESSAGE_LOGIN_REQUEST ="Please login";

    private final String INDEX_PAGE="index";
    private final String LOGIN_FORM="user/loginForm";
    private final String USER_PAGE="user/user";
    private final String USERS_PAGE="user/users";
    private final String REGISTER_PAGE="user/register";
    private final String PROFILE_PAGE="user/profile";
    private final String EDIT_PAGE="user/edit";

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return LOGIN_FORM;
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = (String) session.getAttribute(ATTR_LANGUAGE);
        session.invalidate();
        req.getSession().setAttribute(ATTR_LANGUAGE, lang);
        return INDEX_PAGE;

    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        UserDto user = userService.get(id);
        model.addAttribute(ATTR_USER, user);
        return USER_PAGE;
    }

    @GetMapping()
    public String getAll() {
        return USERS_PAGE;
    }

    @GetMapping("/register")
    public String create() {
        return REGISTER_PAGE;
    }


    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        UserDto user = (UserDto) session.getAttribute(ATTR_USER);
        if (user == null) {
            model.addAttribute(ATTR_MESSAGE, MESSAGE_LOGIN_REQUEST);
            return LOGIN_FORM;
        }
        return PROFILE_PAGE;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        UserDto user = userService.get(id);
        model.addAttribute(ATTR_USER, user);
        return EDIT_PAGE;
    }


}
