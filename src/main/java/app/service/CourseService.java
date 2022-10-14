package app.service;

import app.service.dto.CourseDto;

public interface CourseService extends AbstractService<Long, CourseDto> {

    CourseDto getByName(String name);
}
