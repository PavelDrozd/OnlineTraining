package app.service.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Size;

@Data
public class UserDto {

    private Long id;

    @Size(min = 5, max = 35)
    private String login;

    @Size(min = 4, max = 35)
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
