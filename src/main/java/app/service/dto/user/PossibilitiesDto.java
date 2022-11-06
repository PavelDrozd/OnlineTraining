package app.service.dto.user;

import lombok.Data;

@Data
public class PossibilitiesDto {

    private Long id;

    private UserDto user;

    private boolean watchUsers;

    private boolean deleteUsers;

    private boolean changeRoleUsers;

    private boolean changeUsersParameters;

    private boolean createCourses;

    private boolean deleteCourses;

    private boolean changeCourses;

    private boolean watchDeletedCourses;

    private boolean watchUsersOrders;

    private boolean changeOrderStatus;

    private boolean createLessons;

    private boolean watchLessons;

    private boolean watchDeletedLessons;

    private boolean deleteLessons;

    private boolean changeLessons;
}
