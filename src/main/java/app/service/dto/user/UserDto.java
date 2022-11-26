package app.service.dto.user;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "{errors.user.create.username.empty}")
    @Size(min = 5, message = "{errors.user.create.username.short}")
    @Size(max = 35, message = "{errors.user.create.username.long}")
    private String username;

    @NotBlank(message = "{errors.user.create.password.empty}")
    @Size(min = 4, message = "{errors.user.create.password.short}")
    @Size(max = 35, message = "{errors.user.create.password.long}")
    private String password;

    private PersonalInfoDto personalInfo;

    private Role role;

    public enum Role {
        USER, STUDENT, TUTOR, ADMIN, SUPERADMIN
    }

    @ToString.Include(name = "password")
    public String getFakePassword() {
        return "[PROTECTED]";
    }
}
