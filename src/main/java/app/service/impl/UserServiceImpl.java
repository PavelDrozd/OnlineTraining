package app.service.impl;

import app.exceptions.service.ServiceNotFoundException;
import app.exceptions.service.ServiceValidationException;
import app.log.Logger;
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

    @Logger
    @Override
    public UserDto create(UserDto userDto) {
        checkCreateValidation(userDto);
        userDto.setPassword(digestUtil.hash(userDto.getPassword()));
        userDto.setRole(UserDto.Role.USER);
        User user = userRep.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Logger
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return userRep.findAll(pageable).map(mapper::mapToUserDto);
    }

    @Logger
    @Override
    public UserDto get(Long id) {
        return userRep.findById(id)
                .map(mapper::mapToUserDto)
                .orElseThrow(() -> new ServiceNotFoundException("User with id " + id + " doesn't exist"));
    }

    @Logger
    @Override
    public UserDto update(UserDto userDto) {
        loginAndEmailValidation(userDto);
        User user = userRep.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @Logger
    @Override
    public void delete(Long id) {
        User user = userRep.findById(id).orElseThrow(() -> new ServiceNotFoundException("User doesn't exist"));
        user.setDeleted(false);
    }

    @Logger
    @Override
    public Long count() {
        return userRep.count();
    }

    @Logger
    @Override
    public UserDto login(String username, String password) {
        Optional<User> user = userRep.findByUsername(username);
        String hashedPassword = digestUtil.hash(password);
        if (!user.orElseThrow(() -> new ServiceNotFoundException("User doesn't exist"))
                .getPassword().equals(hashedPassword)) {
            throw new ServiceValidationException("Incorrect login or password");
        }
        return mapper.mapToUserDto(user.get());
    }

    private static void checkCreateValidation(UserDto userDto) {
        if (userDto == null) {
            throw new ServiceValidationException("User for create is null");
        }
    }

    private void loginAndEmailValidation(UserDto userDto) {
        Optional<User> existing = userRep.findById(userDto.getId());
        String existingLogin = existing.orElseThrow(() -> new ServiceNotFoundException("User doesn't exist"))
                .getUsername();
        checkLogin(userDto, existingLogin);
        String existingEmail = existing.orElseThrow(() -> new ServiceNotFoundException("User doesn't exist"))
                .getPersonalInfo().getEmail();
        checkEmail(userDto, existingEmail);
    }

    private void checkLogin(UserDto userDto, String existingLogin) {
        if (!existingLogin.equals(userDto.getUsername())) {
            Optional<User> user = userRep.findByUsername(userDto.getUsername());
            if (user.isPresent()) {
                throw new ServiceValidationException("User with login" + userDto.getUsername() + " already exist");
            }
        }
    }

    private void checkEmail(UserDto userDto, String existingEmail) {
        if (!existingEmail.equals(userDto.getPersonalInfo().getEmail())) {
            Optional<PersonalInfo> personalInfo = personalInfoRep.findByEmail(userDto.getPersonalInfo().getEmail());
            if (personalInfo.isPresent()) {
                throw new ServiceValidationException("User with login" + userDto.getUsername() + " already exist");
            }
        }
    }
}
