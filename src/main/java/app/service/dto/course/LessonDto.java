package app.service.dto.course;

import lombok.Data;

@Data
public class LessonDto {

    private Long id;

    private boolean completed;

    private String name;

    private String lessonInfo;

    private CourseProgressionDto courseProgression;
}
