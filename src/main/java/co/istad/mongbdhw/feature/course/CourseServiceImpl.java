package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.feature.category.CategoryRepository;
import co.istad.mongbdhw.feature.course.dto.*;
import co.istad.mongbdhw.feature.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CourseMapper courseMapper;

    //Create a new course
    @Override
    public void createCourse(CourseCreateRequest courseCreateRequest) {
        //Validate Course
        if (courseRepository.existsByTitle(courseCreateRequest.title())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This Course is already exist!!!");
        }
        //Validate Category
        categoryRepository.findByName(courseCreateRequest.categoryName())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This category has not been found!!!"));
        Course course = courseMapper.fromCourseCreateRequest(courseCreateRequest);
        course.setUuid(UUID.randomUUID().toString());
        course.setUpdatedAt(LocalDateTime.now());
        course.setIsPaid(false);
        course.setIsDrafted(false);
        course.setInstructorUsername("Teacher Chaya");
        courseRepository.save(course);
    }

    //Find all courses
    @Override
    public List<CourseResponse> findAllCourse() {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        List<Course> courses =courseRepository.findAll(sortById);
        return courseMapper.toCourseResponseList(courses);
    }

    //Find course by id
    @Override
    public CourseResponse findCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        return courseMapper.toCourseResponse(course);
    }

    //Update course by id
    @Override
    public CourseResponse updateCourse(String id, CourseUpdateRequest courseUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));

        courseMapper.fromCourseUpdateRequest(courseUpdateRequest, course);
        course = courseRepository.save(course);

        return courseMapper.toCourseResponse(course);
    }

    //Delete course
    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        courseRepository.delete(course);
    }

    //Enable course
    @Override
    public void enableCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        course.setIsDeleted(false);
        courseRepository.save(course);
    }

    //Disable course
    @Override
    public void disableCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        course.setIsDeleted(true);
        courseRepository.save(course);
    }

    @Override
    public void updateThumbnail(String id, UpdateThumbnailRequest updateThumbnail) {
        //Validate course
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        //Validate thumbnail
        if (courseRepository.existsByThumbnail(updateThumbnail.thumbnail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This thumbnail is already exist!!!");
        }
        course.setThumbnail(updateThumbnail.thumbnail());
        courseRepository.save(course);
    }

    @Override
    public void updatePayment(String id, UpdatePaymentRequest updatePaymentRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        course.setIsPaid(updatePaymentRequest.status());
        courseRepository.save(course);
    }
}
