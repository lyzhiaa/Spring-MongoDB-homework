package co.istad.mongbdhw.feature.mapper;

import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.domain.Section;
import co.istad.mongbdhw.feature.course.dto.CourseCreateRequest;
import co.istad.mongbdhw.feature.course.dto.CourseResponse;
import co.istad.mongbdhw.feature.course.dto.CourseUpdateRequest;
import co.istad.mongbdhw.feature.course.dto.SectionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    //Use for create course
    Course fromCourseCreateRequest(CourseCreateRequest courseCreateRequest);
    //Use for find course by id
    CourseResponse toCourseResponse(Course course);
    //Use for list all courses
    List<CourseResponse> toCourseResponseList(List<Course> courses);
    //Use for list section
    SectionResponse toSectionResponse(Section section);
    //Use for update course
    void fromCourseUpdateRequest(CourseUpdateRequest courseUpdateRequest, @MappingTarget Course course);
}
