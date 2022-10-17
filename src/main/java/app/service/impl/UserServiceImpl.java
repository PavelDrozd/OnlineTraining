package app.service.impl;

import app.repository.UserRepository;
import app.repository.entity.User;
import app.exceptions.DaoException;
import app.exceptions.ServiceException;
import app.service.UserService;
import app.service.dto.UserDto;
import app.service.util.DigestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        try {
            log.debug("Service 'create' new userDto: {}.", userDto);
            checkCreateExistsByEmail(userDto);
            String hashedPassword = DigestUtil.INSTANCE.hash(userDto.getPassword());
            userDto.setPassword(hashedPassword);
            User user = userRepository.create(toUserEntity(userDto));
            return toUserDto(user);
        } catch (DaoException e) {
            log.error("Service can't create 'user': {} , throw: {}", userDto, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> getAll(int limit, int offset) {
        try {
            log.debug("Service 'getAll' command request.");
            return userRepository.getAll(limit, offset).stream().map(this::toUserDto).collect(Collectors.toList());
        } catch (DaoException e) {
            log.error("Service can't get all 'users', throw: {}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto getById(Long id) {
        try {
            log.debug("Service 'getById' id: {}.", id);
            if (userRepository.getById(id) == null) {
                throw new RuntimeException("User with ID:" + id + " is null.");
            }
            return toUserDto(userRepository.getById(id));
        } catch (DaoException e) {
            log.error("Service can't get 'user' by id: {} , throw: {}", id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto update(UserDto userDto) {
        try {
            log.debug("Service 'update' userDto: {}.", userDto);
            User existing = userRepository.getByEmail(userDto.getEmail());
            if (existing != null && !existing.getId().equals(userDto.getId())) {
                throw new RuntimeException("User with Email " + userDto.getEmail() + "already exists.");
            }
            User user = userRepository.update(toUserEntity(userDto));
            return toUserDto(user);
        } catch (DaoException e) {
            log.error("Service can't update 'user' to: {} , throw: {}", userDto, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id) {
            log.debug("Service 'delete' by id: {}.", id);
            userRepository.delete(id);
    }

    @Override
    public Integer count() {
        try {
            return userRepository.count();
        } catch (DaoException e) {
            log.error("Service can't count 'users', throw: {}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> getByFirstName(String firstName) {
        try {
            log.debug("Service 'getByLastName' lastName: {}.", firstName);
            return userRepository.getByLastName(firstName).stream().map(this::toUserDto).collect(Collectors.toList());
        } catch (DaoException e) {
            log.error("Service can't get 'users' by firstName: {} , throw: {}", firstName, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserDto> getByLastName(String lastName) {
        try {
            log.debug("Service 'getByLastName' lastName: {}.", lastName);
            return userRepository.getByLastName(lastName).stream().map(this::toUserDto).collect(Collectors.toList());
        } catch (DaoException e) {
            log.error("Service can't get 'users' by lastName: {} , throw: {}", lastName, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto getByEmail(String email) {
        try {
            log.debug("Service 'getByEmail' email: {}.", email);
            return toUserDto(userRepository.getByEmail(email));
        } catch (DaoException e) {
            log.error("Service can't get 'user' by email: {} , throw: {}", email, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto login(String email, String password) {
        try {
            UserDto userDto = getByEmail(email);
            if (userDto == null) {
                throw new RuntimeException("Incorrect email or password");
            }
            String hashedPassword = DigestUtil.INSTANCE.hash(password);
            if (!userDto.getPassword().equals(hashedPassword)) {
                throw new RuntimeException("Incorrect email or password");
            }
            return userDto;
        } catch (DaoException e) {
            log.error("Service can't login 'user', throw: {}", e);
            throw new ServiceException(e);
        }
    }

    private User toUserEntity(UserDto userDto) {
        User user = new User();
        setUserId(userDto, user);
        setUserFirstName(userDto, user);
        setUserLastName(userDto, user);
        setUserAge(userDto, user);
        setUserEmail(userDto, user);
        setUserPassword(userDto, user);
        setUserRole(userDto, user);
        return user;
    }

    private void setUserId(UserDto userDto, User user) {
        if (userDto.getId() != null) {
            user.setId(userDto.getId());
        }
    }

    private void setUserFirstName(UserDto userDto, User user) {
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
    }

    private static void setUserLastName(UserDto userDto, User user) {
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
    }

    private static void setUserAge(UserDto userDto, User user) {
        if (userDto.getAge() != null) {
            user.setAge(userDto.getAge());
        }
    }

    private static void setUserEmail(UserDto userDto, User user) {
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
    }

    private static void setUserPassword(UserDto userDto, User user) {
        if (userDto.getPassword() != null) {
            String hashedPassword = DigestUtil.INSTANCE.hash(userDto.getPassword());
            user.setPassword(hashedPassword);
        }
    }

    private static void setUserRole(UserDto userDto, User user) {
        if (userDto.getRoleDto() == null) {
            userDto.setRoleDto(UserDto.RoleDto.USER);
        }
        user.setRole(User.Role.valueOf(userDto.getRoleDto().toString()));
    }


    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoleDto(UserDto.RoleDto.valueOf(user.getRole().toString()));
        return userDto;
    }

    private void checkCreateExistsByEmail(UserDto userDto) throws DaoException {
        User existing = userRepository.getByEmail(userDto.getEmail());
        if (existing != null) {
            throw new RuntimeException("User with Email " + userDto.getEmail() + "already exists.");
        }
    }

}