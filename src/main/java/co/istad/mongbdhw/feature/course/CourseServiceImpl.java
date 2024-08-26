package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.domain.Section;
import co.istad.mongbdhw.domain.Video;
import co.istad.mongbdhw.feature.section.SectionRepository;
import co.istad.mongbdhw.feature.category.CategoryRepository;
import co.istad.mongbdhw.feature.course.dto.*;
import co.istad.mongbdhw.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CourseMapper courseMapper;
    private final SectionRepository sectionRepository;

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
        course.setInstructorUsername("Teacher Chhaya");
        courseRepository.save(course);
    }

    //Find all courses
    @Override
    public List<?> findAllCourse(String responseType) {

        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        List<Course> courses = courseRepository.findAll(sortById);

        if (responseType.equalsIgnoreCase("content_details")) {
            return courseMapper.toCourseResponseDetailList(courses);
        } else {
            return courseMapper.toCourseResponseList(courses);
        }
    }

    //Get private courses
    /*@Override
    public Optional<Course> getPrivateCourse(Boolean isDrafted) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        List<Course> courses = courseRepository.findAll(sortById);
        return courseRepository.findByIsDrafted(isDrafted);
    }*/
    @Override
    public List<Course> getPrivateCourse() {
        return courseRepository.findByIsDraftedIsTrue();
    }

    //Get public courses
    @Override
    public List<Course> getPublicCourse() {
        return courseRepository.findByIsDraftedIsFalse();
    }

    //Get free course
    @Override
    public List<Course> getFreeCourse() {
        return courseRepository.findByPrice("0");
    }

    //Find course by instructor's name
    @Override
    public CourseResponse findCourseByInstructorName(String instructorUsername) {
        Course course = courseRepository.findByInstructorUsername(instructorUsername)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public CourseResponseDetail findCourseDetail(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        return courseMapper.toCourseResponseDetail(course);
    }

    //Find course by id
    @Override
    public CourseResponse findCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public List<SectionResponse> findSection() {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        List<Section> sections = sectionRepository.findAll(sortById);
        return courseMapper.toSectionResponseList(sections);
    }

    //Find course by slug
    @Override
    public CourseResponse findCourseBySlug(String slug) {
        Course course = courseRepository.findBySlug(slug)
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

    //Update thumbnail status by courseId
    @Override
    public void updateThumbnail(String id, ThumbnailUpdateRequest updateThumbnail) {
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

    //Update payment status by courseId
    @Override
    public void updatePayment(String id, PaymentUpdateRequest updatePaymentRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        course.setIsPaid(updatePaymentRequest.status());
        courseRepository.save(course);
    }

    //Update visibility status by courseId
    @Override
    public void updateVisibility(String id, VisibilityUpdateRequest visibilityUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));
        course.setIsDrafted(visibilityUpdateRequest.status());
        courseRepository.save(course);
    }

    //Create section
    @Override
    public SectionCreateRequest createSection(String courseId, SectionCreateRequest sectionCreateRequest) {
        // Validate Course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found!!!"));

        // Initialize the sections list if it is null
        if (course.getSections() == null) {
            course.setSections(new ArrayList<>());
        }

        // Convert SectionCreateRequest to Section entity
        Section section = courseMapper.fromSectionCreateRequest(sectionCreateRequest);
        section.getVideos().forEach(video -> video.setLectureNo(UUID.randomUUID().toString().substring(0, 8)));

        // Add the new section to the course
        course.getSections().add(section);

        // Save the updated course
        courseRepository.save(course);
        sectionRepository.save(section);

        // Return the appropriate response
        return courseMapper.toSectionCreateRequest(sectionCreateRequest);
    }


    //Create video
    @Override
    public VideoCreateRequest createVideo(String id, VideoCreateRequest videoCreateRequest) {
        // Validate Course
        courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This course has not been found!!!"));

        // Validate Section
        Section section = sectionRepository.findByOrderNo(videoCreateRequest.sectionOrderNo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This section has not been found!!!"));

        // Create and set up Video
        Video video = courseMapper.fromVideoCreateRequest(videoCreateRequest);
        video.setLectureNo(UUID.randomUUID().toString().substring(0, 8));
        // Associate Video with Section
        section.getVideos().add(video);
        sectionRepository.save(section); // Save the updated Section with the new Video
        // Optionally, if you need to return a modified VideoCreateRequest
        return courseMapper.toVideoCreateRequest(videoCreateRequest);
    }


}
