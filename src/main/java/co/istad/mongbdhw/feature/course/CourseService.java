package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.feature.course.dto.FilterDto;
import co.istad.mongbdhw.feature.course.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    //Create course
    void createCourse(CourseCreateRequest courseCreateRequest);
    //Find all course
    PaginatedResponse<?> findAllCourse(String responseType,int page, int size);

    //Get private courses
//    Optional<Course> getPrivateCourse(Boolean isDrafted);
    List<Course> getPrivateCourse();
    //Get public courses
    List<Course> getPublicCourse();

    //Get free courses
    List<Course> getFreeCourse();

    //Find course by instructor's name
    CourseResponse findCourseByInstructorName(String instructorUsername);

    //Find course detail by id
    CourseResponseDetail findCourseDetail(String id);

    //Find course by id
    CourseResponse findCourseById(String id);
    //Find course by id
    List<SectionResponse> findSection();

    //Find course by slug
    CourseResponse findCourseBySlug(String slug);

    //Update course by id
    CourseResponse updateCourse(String id, CourseUpdateRequest courseUpdateRequest);

    //Delete course by id
    void deleteCourse(String id);

    //Enable course by id
    void enableCourse(String id);

    //Disable course by id
    void disableCourse(String id);

    //Update thumbnail by courseId
    void updateThumbnail(String id, ThumbnailUpdateRequest updateThumbnail);

    //Update payment status by courseId
    void updatePayment(String id, PaymentUpdateRequest updatePaymentRequest);

    //Update visibility
    void updateVisibility(String id, VisibilityUpdateRequest visibilityUpdateRequest);

    //Update video by courseId
    void updateVideo(String id, VideoUpdateRequest videoUpdateRequest);

    //Create a video
    VideoCreateRequest createVideo(String id, VideoCreateRequest videoCreateRequest);

    //Create section
    SectionCreateRequest createSection(String id, SectionCreateRequest sectionCreateRequest);

    Page<?> createFilter(FilterDto filterDto, FilterResponse filterResponse, int page, int size);

    Page<?> getFilter(String title, FilterResponse filterResponse, int page, int size);

}
