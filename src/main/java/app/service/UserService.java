package app.service;

import app.service.dto.user.UserDto;

public interface UserService extends AbstractService<UserDto, Long> {

    UserDto findByEmail(String email);

    UserDto login(String email, String password);

}
