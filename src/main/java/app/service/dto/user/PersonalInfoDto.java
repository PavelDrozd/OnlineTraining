package app.service.dto.user;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class PersonalInfoDto {

    private Long id;

    @NotBlank(message = "Firstname is required")
    @Size(max = 15, message = "Firstname is too long")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(max = 15, message = "Lastname is too long")
    private String lastName;

    @Size(max = 15, message = "Patronymic is too long")
    private String patronymic;

    @NotBlank(message = "Email is required")
    @Size(max = 35, message = "Email is too long")
    @Size(min = 5, message = "Email is too short")
    private String email;

    private LocalDate dayOfBirth;

    private boolean deleted;

    @ToString.Exclude
    private UserDto user;
}
