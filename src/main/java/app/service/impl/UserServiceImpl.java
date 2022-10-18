package app.service.impl;

import app.interceptors.LogInvocation;
import app.repository.UserRep;
import app.repository.entity.User;
import app.exceptions.ServiceException;
import app.service.UserService;
import app.service.dto.UserDto;
import app.service.util.DigestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRep userRep;
    private final DigestUtil digestUtil;

    @LogInvocation
    @Override
    public UserDto create(UserDto userDto) {
        checkCreateExistsByEmail(userDto);
        String hashedPassword = digestUtil.hash(userDto.getPassword());
        userDto.setPassword(hashedPassword);
        User user = userRep.save(toUserEntity(userDto));
        return toUserDto(user);
    }

    @LogInvocation
    @Override
    public List<UserDto> findAll(Pageable pageable) {
        List<User> users = userRep.findByDeletedFalse(pageable).stream().toList();
        return users.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @LogInvocation
    @Override
    public UserDto findById(Long id) {
        Optional<User> user = userRep.findByIdAndDeletedFalse(id);
        if (user.isEmpty()) {
            throw new ServiceException("User with id " + id + " doesn't exist");
        }
        return toUserDto(user.get());
    }

    @LogInvocation
    @Override
    public UserDto update(UserDto userDto) {
        Optional<User> existing = userRep.findById(userDto.getId());
        if (existing.isPresent() && existing.get().getEmail().equals(userDto.getEmail())) {
            throw new ServiceException("User with email " + userDto.getEmail() + "already exists.");
        }
        User user = userRep.save(toUserEntity(userDto));
        return toUserDto(user);
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        User user = userRep.findById(id).orElseThrow(() -> new ServiceException("Course doesn't exist"));
        user.setDeleted(false);
    }

    @LogInvocation
    @Override
    public Long count() {
        return userRep.count();
    }

    @LogInvocation
    @Override
    public UserDto findByEmail(String email) {
        return toUserDto(userRep.getByEmail(email));
    }

    @LogInvocation
    @Override
    public UserDto login(String email, String password) {
        UserDto userDto = findByEmail(email);
        if (userDto == null) {
            throw new ServiceException("Incorrect email or password");
        }
        String hashedPassword = digestUtil.hash(password);
        if (!userDto.getPassword().equals(hashedPassword)) {
            throw new ServiceException("Incorrect email or password");
        }
        return userDto;
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

    private void setUserLastName(UserDto userDto, User user) {
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
    }

    private void setUserAge(UserDto userDto, User user) {
        if (userDto.getAge() != null) {
            user.setAge(userDto.getAge());
        }
    }

    private void setUserEmail(UserDto userDto, User user) {
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
    }

    private void setUserPassword(UserDto userDto, User user) {
        if (userDto.getPassword() != null) {
            String hashedPassword = digestUtil.hash(userDto.getPassword());
            user.setPassword(hashedPassword);
        }
    }

    private void setUserRole(UserDto userDto, User user) {
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

    private void checkCreateExistsByEmail(UserDto userDto){
        User existing = userRep.getByEmail(userDto.getEmail());
        if (existing != null) {
            throw new RuntimeException("User with Email " + userDto.getEmail() + "already exists.");
        }
    }

}
