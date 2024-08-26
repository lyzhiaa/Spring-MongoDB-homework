package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.feature.course.dto.*;

import java.util.List;

public interface CourseService {
    //Create course
    void createCourse(CourseCreateRequest courseCreateRequest);
    //Find all category
    List<?> findAllCourse(String responseType);

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

    //Create a video
    VideoCreateRequest createVideo(String id, VideoCreateRequest videoCreateRequest);

    //Create section
    SectionCreateRequest createSection(String id, SectionCreateRequest sectionCreateRequest);

}
