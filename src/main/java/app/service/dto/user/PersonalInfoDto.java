package app.service.dto.user;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
public class PersonalInfoDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String email;

    private LocalDate dayOfBirth;

    private boolean deleted;

    @ToString.Exclude
    private UserDto user;
}
