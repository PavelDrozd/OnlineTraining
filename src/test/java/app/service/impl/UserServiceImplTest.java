package app.service.impl;

import app.exceptions.service.ServiceNotFoundException;
import app.exceptions.service.ServiceValidationException;
import app.repository.PersonalInfoRep;
import app.repository.UserRep;
import app.repository.entity.user.PersonalInfo;
import app.repository.entity.user.User;
import app.service.UserService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.user.UserDto;
import app.service.util.DigestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceImplTest {
    private static UserService userService;

    private static final Long INVALID_USER_ID = Long.MIN_VALUE;
    private static final Long VALID_USER_ID = 1L;
    private static final String INVALID_USER_USERNAME = "u";
    private static final String VALID_USER_USERNAME = "test";
    private static final String INVALID_USER_PASSWORD = "p";
    private static final String INVALID_PERSONALINFO_EMAIL = "email";
    private static final String VALID_PERSONALINFO_EMAIL = "test@gmail.com";

    @BeforeAll
    static void beforeAll() {
        UserRep userRep = Mockito.mock(UserRep.class);
        PersonalInfoRep personalInfoRep = Mockito.mock(PersonalInfoRep.class);

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(1L);
        personalInfo.setDeleted(false);
        personalInfo.setFirstName("testName");
        personalInfo.setLastName("testLastname");
        personalInfo.setPatronymic("testPatronymic");
        personalInfo.setEmail("test@gmail.com");
        personalInfo.setDayOfBirth(LocalDate.of(2000, 1, 1));

        Mockito.when(personalInfoRep.findByEmail(VALID_PERSONALINFO_EMAIL)).thenReturn(Optional.of(personalInfo));

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("password");
        user.setPersonalInfo(personalInfo);
        user.setRole(User.Role.USER);
        user.setEnabled(true);
        user.setDeleted(false);

        Mockito.when(userRep.save(user)).thenReturn(user);
        Mockito.when(userRep.findById(VALID_USER_ID)).thenReturn(Optional.of(user));
        Mockito.when(userRep.findByUsername(VALID_USER_USERNAME)).thenReturn(Optional.of(user));

        EntityDtoMapper mapper = new EntityDtoMapper(new ModelMapper());
        userService = new UserServiceImpl(userRep, personalInfoRep, new DigestUtil(), mapper);
    }

    @Test
    void createUserNullInExceptionOut() {
        assertThrows(ServiceValidationException.class, () -> userService.create(null));
    }

    @Test
    void getUserByID() {
        UserDto actual = userService.get(VALID_USER_ID);
        assertEquals(VALID_USER_ID, actual.getId());
    }

    @Test
    void getUserNotFound() {
        assertThrows(ServiceNotFoundException.class, () -> userService.get(INVALID_USER_ID));
    }

    @Test
    void updateUserWithInvalidID() {
        UserDto user = new UserDto();
        user.setId(INVALID_USER_ID);
        assertThrows(ServiceNotFoundException.class, () -> userService.update(user));
    }

    @Test
    void updateUserWithInvalidEmail() {
        UserDto user = new UserDto();
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setEmail(INVALID_PERSONALINFO_EMAIL);
        assertThrows(ServiceNotFoundException.class, () -> userService.update(user));
    }

    @Test
    void deleteUserWithInvalidID() {
        assertThrows(ServiceNotFoundException.class, () -> userService.delete(INVALID_USER_ID));
    }

    @Test
    void loginUserWithInvalidUsernameAndPassword() {
        assertThrows(ServiceNotFoundException.class, () -> userService.login(INVALID_USER_USERNAME, INVALID_USER_PASSWORD));
    }

    @Test
    void loginUserWithInvalidPassword() {
        assertThrows(ServiceValidationException.class, () -> userService.login(VALID_USER_USERNAME, INVALID_USER_PASSWORD));
    }

}
