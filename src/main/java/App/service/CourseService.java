package App.service;

import App.service.dto.CourseDto;

public interface CourseService extends AbstractService<Long, CourseDto> {

    CourseDto getByName(String name);
}
