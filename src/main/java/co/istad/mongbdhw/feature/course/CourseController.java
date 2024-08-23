package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.feature.course.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    //Create a new course
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createCourse(@RequestBody CourseCreateRequest courseCreateRequest) {
        courseService.createCourse(courseCreateRequest);
    }

    //Find all courses
    @GetMapping
    List<CourseResponse> findAllCourse() {
        return courseService.findAllCourse();
    }

    //Find course By id
    @GetMapping("/{id}")
    CourseResponse findCourseById(@Valid @PathVariable String id) {
        return courseService.findCourseById(id);
    }

    //Update course by id
    @PutMapping("/{id}")
    CourseResponse updateCourse(@Valid @PathVariable String id, @RequestBody CourseUpdateRequest courseUpdateRequest) {
        return courseService.updateCourse(id, courseUpdateRequest);
    }

    //Delete course by id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteCourse(@Valid @PathVariable String id) {
        courseService.deleteCourse(id);
    }

    //Enable course by id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/enable")
    void enableCourse(@Valid @PathVariable("id") String id) {
        courseService.enableCourse(id);
    }

    //Disable course by id
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/disable")
    void disableCourse(@Valid @PathVariable("id") String id) {
        courseService.disableCourse(id);
    }

    //Update thumbnail by courseId
    @PutMapping("/{id}/thumbnail")
    void updateThumbnail(@Valid @PathVariable String id, @RequestBody UpdateThumbnailRequest updateThumbnail) {
        courseService.updateThumbnail(id, updateThumbnail);
    }

    //Update Payment by courseId
    @PutMapping("/{id}/is-paid")
    void updatePayment(@Valid @PathVariable String id, @RequestBody UpdatePaymentRequest updatePaymentRequest) {
        courseService.updatePayment(id, updatePaymentRequest);
    }

}
