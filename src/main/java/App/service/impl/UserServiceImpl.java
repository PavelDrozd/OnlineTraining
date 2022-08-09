package App.service.impl;

import App.dao.UserDao;
import App.dao.entity.User;
import App.service.UserService;
import App.service.dto.UserDto;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("Service 'create' new userDto: {}.", userDto);
        User existing = userDao.getByEmail(userDto.getEmail());
        if (existing != null) {
            throw new RuntimeException("Book with Email " + userDto.getEmail() + "already exists.");
        }
        User user = userDao.create(toUserEntity(userDto));
        return toUserDto(user);
    }

    @Override
    public List<UserDto> getAll(int limit, long offset) {
        log.debug("Service 'getAll' command request.");
        return userDao.getAll(limit, offset).stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        log.debug("Service 'getById' id: {}.", id);
        if (userDao.getById(id) == null) {
            throw new RuntimeException("User with ID:" + id + " is null.");
        }
        return toUserDto(userDao.getById(id));
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.debug("Service 'update' userDto: {}.", userDto);
        User existing = userDao.getByEmail(userDto.getEmail());
        if (existing != null && !existing.getId().equals(userDto.getId())) {
            throw new RuntimeException("Book with Email " + userDto.getEmail() + "already exists.");
        }
        User user = userDao.update(toUserEntity(userDto));
        return toUserDto(user);
    }

    @Override
    public void delete(Long id) {
        log.debug("Service 'delete' by id: {}.", id);
        if (userDao.delete(id)) {
            throw new RuntimeException("Can't delete user by id: " + id);
        }
    }

    @Override
    public Long count() {
        return userDao.count();
    }

    @Override
    public List<UserDto> getByFirstName(String firstName) {
        log.debug("Service 'getByLastName' lastName: {}.", firstName);
        return userDao.getByLastName(firstName).stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getByLastName(String lastName) {
        log.debug("Service 'getByLastName' lastName: {}.", lastName);
        return userDao.getByLastName(lastName).stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getByEmail(String email) {
        log.debug("Service 'getByEmail' email: {}.", email);
        return toUserDto(userDao.getByEmail(email));
    }

    @Override
    public UserDto login(String email, String password) {
        UserDto userDto = getByEmail(email);
        if (userDto == null) {
            throw new RuntimeException("Incorrect email or password");
        }
        if (!userDto.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect email or password");
        }

        return userDto;
    }

    private User toUserEntity(UserDto userDto) {
        User user = new User();
        if (user.getId() != null) {
            user.setId(userDto.getId());
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        if (userDto.getRoleDto() == null){
            userDto.setRoleDto(UserDto.RoleDto.USER);
        }
        user.setRole(User.Role.values()[userDto.getRoleDto().ordinal()]);
        return user;
    }

    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoleDto(UserDto.RoleDto.values()[user.getRole().ordinal()]);
        return userDto;
    }
}
