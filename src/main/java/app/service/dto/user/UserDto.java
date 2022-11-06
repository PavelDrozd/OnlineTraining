package app.service.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "Login is required")
    @Size(min = 5, message = "TLogin is too short")
    @Size(max = 35, message = "Login is too long")
    private String login;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password is too short")
    @Size(max = 35, message = "Password is too long")
    @JsonIgnore
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
