package app.service.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class PersonalInfoDto {

    private Long id;

    @NotBlank(message = "{errors.personalifno.create.firstname.empty}")
    @Size(max = 15, message = "{errors.personalifno.create.firstname.long}")
    private String firstName;

    @NotBlank(message = "{errors.personalifno.create.lastname.empty}")
    @Size(max = 15, message = "{errors.personalifno.create.lastname.long}")
    private String lastName;

    @Size(max = 15, message = "{errors.personalifno.create.patronymic.long}")
    private String patronymic;

    @NotBlank(message = "{errors.personalifno.create.email.empty}")
    @Size(max = 35, message = "{errors.personalifno.create.email.long}")
    @Size(min = 5, message = "{errors.personalifno.create.email.short}")
    private String email;

    private LocalDate dayOfBirth;

    private boolean deleted;

}
