package app.service.dto.course;

import app.service.dto.user.UserDto;
import lombok.Data;

@Data
public class CourseProgressionDto {

    private Long id;

    private CourseDto course;

    private UserDto user;

    private Integer completedLessons;
}
