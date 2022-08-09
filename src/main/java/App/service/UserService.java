package App.service;

import App.service.dto.UserDto;

import java.util.List;

public interface UserService extends AbstractService<Long, UserDto>{

    List<UserDto> getByFirstName(String firstName);

    List<UserDto> getByLastName(String lastName);

    UserDto getByEmail(String email);

    UserDto login(String email, String password);
}
