package co.istad.mongbdhw.mapper;

import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.domain.Section;
import co.istad.mongbdhw.domain.Video;
import co.istad.mongbdhw.feature.course.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    //Use for create course
    Course fromCourseCreateRequest(CourseCreateRequest courseCreateRequest);
    Section fromSectionCreateRequest(SectionCreateRequest sectionCreateRequest);
    //Use for create video by courseId
    Video fromVideoCreateRequest(VideoCreateRequest videoCreateRequest);
    //Use for find course by id
    CourseResponse toCourseResponse(Course course);
    CourseResponseDetail toCourseResponseDetail(Course courses);
    //Use for list all courses
    List<CourseResponse> toCourseResponseList(List<Course> courses);
    List<CourseResponseDetail> toCourseResponseDetailList(List<Course> courses);
//    Use for list all section
    List<SectionResponse> toSectionResponseList(List<Section> sections);
    //Use for list section
    @Mapping(target = "videos", source = "videos")
    SectionResponse toSectionResponse(Section section);
    //Use for update course
    void fromCourseUpdateRequest(CourseUpdateRequest courseUpdateRequest, @MappingTarget Course course);
    //Use for create section by courseId
    void fromSectionCreateRequest(SectionCreateRequest sectionCreateRequest, @MappingTarget Course course);
    //Video response
    @Mapping(target = "sectionOrderNo", source = "sectionOrderNo")
    VideoCreateRequest toVideoCreateRequest(VideoCreateRequest videoCreateRequest);
    SectionCreateRequest toSectionCreateRequest(SectionCreateRequest sectionCreateRequest);

}
