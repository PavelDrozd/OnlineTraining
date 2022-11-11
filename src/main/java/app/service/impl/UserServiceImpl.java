package app.service.impl;

import app.exceptions.service.ServiceException;
import app.interceptors.LogInvocation;
import app.repository.PersonalInfoRep;
import app.repository.PosibilitiesRep;
import app.repository.UserRep;
import app.repository.entity.user.PersonalInfo;
import app.repository.entity.user.User;
import app.service.UserService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.user.UserDto;
import app.service.util.DigestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRep userRep;
    private final PersonalInfoRep personalInfoRep;
    private final PosibilitiesRep posibilitiesRep;
    private final DigestUtil digestUtil;
    private final EntityDtoMapper mapper;

    @LogInvocation
    @Override
    public UserDto create(UserDto userDto) {
        userDto.setPassword(digestUtil.hash(userDto.getPassword()));
        userDto.setRole(UserDto.Role.USER);
        loginAndEmailValidation(userDto);
        User user = userRep.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @LogInvocation
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return userRep.findAll(pageable).map(mapper::mapToUserDto);
    }

    @LogInvocation
    @Override
    public UserDto get(Long id) {
        return userRep.findById(id)
                .map(mapper::mapToUserDto)
                .orElseThrow(() -> new ServiceException("User with id " + id + " doesn't exist"));
    }

    @LogInvocation
    @Override
    public UserDto update(UserDto userDto) {
        loginAndEmailValidation(userDto);
        User user = userRep.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @LogInvocation
    @Override
    public void delete(Long id) {
        User user = userRep.findById(id).orElseThrow(() -> new ServiceException("User doesn't exist"));
        user.setDeleted(false);
    }

    @LogInvocation
    @Override
    public Long count() {
        return userRep.count();
    }

    @LogInvocation
    @Override
    public UserDto login(String login, String password) {
        Optional<User> user = userRep.findByLogin(login);
        String hashedPassword = digestUtil.hash(password);
        if (!user.orElseThrow(() -> new ServiceException("User doesn't exist"))
                .getPassword().equals(hashedPassword)) {
            throw new ServiceException("Incorrect login or password");
        }
        return mapper.mapToUserDto(user.get());
    }

    private void loginAndEmailValidation(UserDto userDto) {
        Optional<User> existing = userRep.findById(userDto.getId());
        String existingLogin = existing.orElseThrow(() -> new ServiceException("User doesn't exist"))
                .getLogin();
        checkLogin(userDto, existingLogin);
        String existingEmail = existing.orElseThrow(() -> new ServiceException("User doesn't exist"))
                .getPersonalInfo().getEmail();
        checkEmail(userDto, existingEmail);
    }

    private void checkLogin(UserDto userDto, String existingLogin) {
        if (!existingLogin.equals(userDto.getLogin())) {
            Optional<User> user = userRep.findByLogin(userDto.getLogin());
            if (user.isPresent()) {
                throw new ServiceException("User with login" + userDto.getLogin() + " already exist");
            }
        }
    }

    private void checkEmail(UserDto userDto, String existingEmail) {
        if (!existingEmail.equals(userDto.getPersonalInfo().getEmail())) {
            Optional<PersonalInfo> personalInfo = personalInfoRep.findByEmail(userDto.getPersonalInfo().getEmail());
            if (personalInfo.isPresent()) {
                throw new ServiceException("User with login" + userDto.getLogin() + " already exist");
            }
        }
    }
}
