package app.controllers;

import app.service.UserService;
import app.service.dto.UserDto;
import app.service.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PaginationUtil paginationUtil;

    @GetMapping("/login")
    public String loginForm() {
        return "user/loginForm";
    }

    @RequestMapping(value = "/login_user", method = RequestMethod.POST)
    public String login(UserDto userLogin, HttpSession session, Model model) {
        UserDto user = userService.login(userLogin.getEmail(), userLogin.getPassword());
        session.setAttribute("user", user);
        model.addAttribute("message", "Succesfully login!");
        return "main";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = (String) session.getAttribute("lang");
        session.invalidate();
        req.getSession().setAttribute("lang", lang);
        return "main";

    }

    @GetMapping("/user/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/user/list")
    public String showUsers(String limit, String page, Model model) {
        int pageLimit = paginationUtil.getPageSize(limit);
        long totalPages = paginationUtil.getTotalPages(pageLimit, userService.count());
        int pageNumber = paginationUtil.getPage(page, totalPages);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNumber - 1, pageLimit, sort);
        List<UserDto> users = userService.findAll(pageable);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/register")
    public String registerUserForm() {
        return "user/registerUserForm";
    }

    @RequestMapping(value = "/register_new_user", method = RequestMethod.POST)
    public String registerUser(UserDto user, Model model) {
        UserDto created = userService.create(user);
        model.addAttribute("user", created);
        model.addAttribute("message", "New user was created!");
        return "main";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        UserDto user = (UserDto) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "Please login.");
            return "user/loginFrom";
        }
        return "user/profile/profile";
    }

    @GetMapping("/edit_name")
    public String editNameForm() {
        return "user/edit/editNameForm";
    }

    @RequestMapping(value = "/edit_user_name", method = RequestMethod.POST)
    public String editName(HttpSession session, UserDto user, Model model) {
        UserDto userForUpdate = (UserDto) session.getAttribute("user");
        userForUpdate.setFirstName(user.getFirstName());
        userForUpdate.setLastName(user.getLastName());
        UserDto edited = userService.update(userForUpdate);
        model.addAttribute("user", edited);
        model.addAttribute("message", "User name has been changed.");
        return "user/profile/profile";
    }

    @GetMapping("/edit_email")
    public String editEmailForm() {
        return "user/edit/editEmailForm";
    }

    @RequestMapping(value = "/edit_user_email", method = RequestMethod.POST)
    public String editEmail(HttpSession session, UserDto user, Model model) {
        UserDto userForUpdate = (UserDto) session.getAttribute("user");
        userForUpdate.setEmail(user.getEmail());
        UserDto edited = userService.update(userForUpdate);
        model.addAttribute("user", edited);
        model.addAttribute("message", "Email has been changed.");
        return "user/profile/profile";
    }

    @GetMapping("/edit_password")
    public String editPasswordForm() {
        return "user/edit/editPasswordForm";
    }

    @RequestMapping(value = "/edit_user_password", method = RequestMethod.POST)
    public String editPassword(HttpSession session, UserDto user, Model model) {
        UserDto userForUpdate = (UserDto) session.getAttribute("user");
        userForUpdate.setPassword(user.getPassword());
        UserDto edited = userService.update(userForUpdate);
        model.addAttribute("user", edited);
        model.addAttribute("message", "User name has been changed.");
        return "user/profile/profile";
    }

    @GetMapping("/edit_age")
    public String editAgeForm() {
        return "user/edit/editAgeForm";
    }

    @RequestMapping(value = "/edit_user_age", method = RequestMethod.POST)
    public String editAge(HttpSession session, UserDto user, Model model) {
        UserDto userForUpdate = (UserDto) session.getAttribute("user");
        userForUpdate.setAge(user.getAge());
        UserDto edited = userService.update(userForUpdate);
        model.addAttribute("user", edited);
        model.addAttribute("message", "The age has been changed.");
        return "user/profile/profile";
    }


}
