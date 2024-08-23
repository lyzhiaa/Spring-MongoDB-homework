package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.feature.course.dto.*;

import java.util.List;

public interface CourseService {
    //Create course
    void createCourse(CourseCreateRequest courseCreateRequest);
    //Find all category
    List<CourseResponse> findAllCourse();

    //Find course by id
    CourseResponse findCourseById(String id);

    //Update course by id
    CourseResponse updateCourse(String id, CourseUpdateRequest courseUpdateRequest);

    //Delete course by id
    void deleteCourse(String id);

    //Enable course by id
    void enableCourse(String id);

    //Disable course by id
    void disableCourse(String id);

    //Update thumbnail by courseId
    void updateThumbnail(String id, UpdateThumbnailRequest updateThumbnail);

    //Update payment status by courseId
    void updatePayment(String id, UpdatePaymentRequest updatePaymentRequest);
}
