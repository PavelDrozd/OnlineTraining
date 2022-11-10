package app.web.rest;

import app.exceptions.controller.ValidationException;
import app.service.UserService;
import app.service.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final String ATTR_USER ="user";
    private final String ATTR_MESSAGE ="user";
    private final String MESSAGE_SUCCEFULLY_LOGIN ="Succesfully login";
    private static final String INDEX_PAGE = "index";

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return userService.get(id);
    }


    @GetMapping
    public Page<UserDto> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid UserDto userDto, HttpSession session, Model model) {
        UserDto user = userService.login(userDto.getLogin(), userDto.getPassword());
        session.setAttribute(ATTR_USER, user);
        model.addAttribute(ATTR_MESSAGE, MESSAGE_SUCCEFULLY_LOGIN);
        return INDEX_PAGE;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto user, Errors errors) {
        checkErrors(errors);
        UserDto created = userService.create(user);
        return buildResponseUser(created);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserDto user, Errors errors) {
        checkErrors(errors);
        user.setId(id);
        return userService.update(user);
    }

    @PatchMapping("/{id}")
    public UserDto updatePart(@PathVariable Long id, @RequestBody @Valid UserDto user) {
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    private void checkErrors(Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

    private ResponseEntity<UserDto> buildResponseUser(UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(user))
                .body(user);
    }

    private URI getLocation(UserDto user) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }

}
