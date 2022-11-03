package app.service.impl;

import app.exceptions.ServiceException;
import app.interceptors.LogInvocation;
import app.repository.UserRep;
import app.repository.entity.user.User;
import app.service.UserService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.user.UserDto;
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
    private final EntityDtoMapper mapper;

    @LogInvocation
    @Override
    public UserDto create(UserDto userDto) {
        userDto.setPassword(userDto.getPassword());
        User user = userRep.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
    }

    @LogInvocation
    @Override
    public List<UserDto> findAll(Pageable pageable) {
        List<User> users = userRep.findAll(pageable).stream().toList();
        return users.stream().map(mapper::mapToUserDto).collect(Collectors.toList());
    }

    @LogInvocation
    @Override
    public UserDto findById(Long id) {
        Optional<User> user = userRep.findById(id);
        if (user.isEmpty()) {
            throw new ServiceException("User with id " + id + " doesn't exist");
        }
        return mapper.mapToUserDto(user.get());
    }

    @LogInvocation
    @Override
    public UserDto update(UserDto userDto) {
        Optional<User> existing = userRep.findById(userDto.getId());
        if (existing.isPresent()) {
            throw new ServiceException("User with email " + userDto.getPersonalInfo().getEmail() + "already exists.");
        }
        User user = userRep.save(mapper.mapToUser(userDto));
        return mapper.mapToUserDto(user);
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
        return null;
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

}
