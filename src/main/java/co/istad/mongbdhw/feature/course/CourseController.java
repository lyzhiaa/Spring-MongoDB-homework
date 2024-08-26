package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.feature.course.dto.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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
//    @GetMapping
//    List<CourseResponse> findAllCourse(String responseType) {
//        return courseService.findAllCourse(responseType);
//    }
    //Find all section
    @GetMapping("/sections")
    List<SectionResponse> findAllSection() {
        return courseService.findSection();
    }
    @GetMapping
    public List<?> getAllCourses(
            @Parameter(
                    in = ParameterIn.QUERY,
                    schema = @Schema(type = "string", allowableValues = {"snippet", "content_details"})
            )
            @RequestParam(defaultValue = "snippet") String responseType) {

        // Call the service method with the given responseType
        return courseService.findAllCourse(responseType);
    }

    //Get course by instructor's name
    @GetMapping("/instructor/{instructorName}")
    CourseResponse findCourseByInstructorName(@Valid @PathVariable String instructorName) {
        return courseService.findCourseByInstructorName(instructorName);
    }
    //Find course By id
    @GetMapping("/{id}")
    CourseResponse findCourseById(@Valid @PathVariable String id) {
        return courseService.findCourseById(id);
    }

    //Find course detail By id
    @GetMapping("/detail/{id}")
    CourseResponseDetail findCourseDetailById(@Valid @PathVariable String id) {

        return courseService.findCourseDetail(id);
    }

    //Find course By slug
    @GetMapping("/slug/{slug}")
    CourseResponse findCourseBySlug(@Valid @PathVariable String slug) {
        return courseService.findCourseBySlug(slug);
    }

    //Get private course
    /*@GetMapping("/private")
    Optional<Course> getPrivateCourse(@RequestParam(required = false) Boolean isDraft) {
        return courseService.getPrivateCourse(isDraft);
    }*/
    @GetMapping("/private")
    public List<Course> getPrivateCourse() {
        return courseService.getPrivateCourse();
    }

    //Get public courses
    @GetMapping("/public")
    public List<Course> getPublicCourse() {
        return courseService.getPublicCourse();
    }

    //Get free courses
    @GetMapping("/free")
    public List<Course> getFreeCourse() {
        return courseService.getFreeCourse();
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
    void updateThumbnail(@Valid @PathVariable String id, @RequestBody ThumbnailUpdateRequest updateThumbnail) {
        courseService.updateThumbnail(id, updateThumbnail);
    }

    //Update Payment by courseId
    @PutMapping("/{id}/is-paid")
    void updatePayment(@Valid @PathVariable String id, @RequestBody PaymentUpdateRequest updatePaymentRequest) {
        courseService.updatePayment(id, updatePaymentRequest);
    }

    //Update visibility by courseId
    @PutMapping("/{id}/visibilities")
    void updateVisibility(@Valid @PathVariable String id, @RequestBody VisibilityUpdateRequest visibilityUpdateRequest) {
        courseService.updateVisibility(id, visibilityUpdateRequest);
    }

    //Create Video by courseId
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{courseId}/videos")
    VideoCreateRequest createVideo(@Valid @PathVariable String courseId, @RequestBody VideoCreateRequest videoCreateRequest) {
        return courseService.createVideo(courseId, videoCreateRequest);
    }
    //Create section by courseId
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{courseId}/sections")
    SectionCreateRequest createSection(@Valid @PathVariable String courseId, @RequestBody SectionCreateRequest sectionCreateRequest) {
        return courseService.createSection(courseId, sectionCreateRequest);
    }


}
