package App.service.dto;

import lombok.Data;
import lombok.ToString;

@ToString(exclude = "password")
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private RoleDto roleDto;

    public enum RoleDto {
        USER, STUDENT, TEACHER, ADMIN
    }
}
