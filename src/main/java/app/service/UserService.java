package app.service;

import app.service.dto.user.UserDto;

public interface UserService extends AbstractService<UserDto, Long> {

    UserDto login(String login, String password);

}
