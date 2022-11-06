package app.repository.entity.converters;


import app.repository.entity.user.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<User.Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(User.Role attribute) {
        return switch (attribute) {
            case USER -> 1;
            case STUDENT -> 2;
            case TUTOR -> 3;
            case ADMIN -> 4;
            case SUPERADMIN -> 5;
        };
    }

    @Override
    public User.Role convertToEntityAttribute(Integer dbData) {
        return switch (dbData) {
            case 2 -> User.Role.STUDENT;
            case 3 -> User.Role.TUTOR;
            case 4 -> User.Role.ADMIN;
            case 5 -> User.Role.SUPERADMIN;
            default -> User.Role.USER;
        };
    }
}
