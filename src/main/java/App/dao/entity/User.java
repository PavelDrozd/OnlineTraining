package App.dao.entity;

import lombok.Data;
import lombok.ToString;

@ToString (exclude = "password")
@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private Role role;

    public enum Role{
        USER, STUDENT, TEACHER, ADMIN
    }

}
