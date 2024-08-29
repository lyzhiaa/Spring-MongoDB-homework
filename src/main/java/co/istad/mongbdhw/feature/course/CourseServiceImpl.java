package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.base.FilterByRequestBody;
import co.istad.mongbdhw.domain.Course;
import co.istad.mongbdhw.domain.Section;
import co.istad.mongbdhw.domain.Video;
import co.istad.mongbdhw.feature.course.dto.FilterDto;
import co.istad.mongbdhw.feature.section.SectionRepository;
import co.istad.mongbdhw.feature.category.CategoryRepository;
import co.istad.mongbdhw.feature.course.dto.*;
import co.istad.mongbdhw.mapper.CourseMapper;
import co.istad.mongbdhw.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final MongoTemplate mongoTemplate;

    //Create a new course
    @Override
    public void createCourse(CourseCreateRequest courseCreateRequest) {
        //Validate Course
        if (courseRepository.existsByTitle(courseCreateRequest.title())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This Course is already exist");
        }
        //Validate Category
        categoryRepository.findByName(courseCreateRequest.categoryName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This category has not been found"));
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
    public PaginatedResponse<?> findAllCourse(String responseType, int page, int size) {
        // Create a PageRequest object for pagination
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        // Fetch the page of courses using the repository
        Page<Course> pagedCourses = courseRepository.findAll(pageRequest);
        List<Course> courses = pagedCourses.getContent();

        // Prepare the list based on the response type
        List<?> responseList;
        if (responseType.equalsIgnoreCase("content_details")) {
            responseList = courseMapper.toCourseResponseDetailList(courses);
        } else {
            responseList = courseMapper.toCourseResponseList(courses);
        }

        // Create the PageMetadata object using Lombok's constructor
        PaginatedResponse.PageMetadata pageMetadata = new PaginatedResponse.PageMetadata(
                pagedCourses.getSize(),
                pagedCourses.getNumber(),
                pagedCourses.getTotalElements(),
                pagedCourses.getTotalPages()
        );

        // Return the paginated response using Lombok's constructor
        return new PaginatedResponse<>(responseList, pageMetadata);
    }

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public CourseResponseDetail findCourseDetail(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        return courseMapper.toCourseResponseDetail(course);
    }

    //Find course by id
    @Override
    public CourseResponse findCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        return courseMapper.toCourseResponse(course);
    }

    //Update course by id
    @Override
    public CourseResponse updateCourse(String id, CourseUpdateRequest courseUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));

        courseMapper.fromCourseUpdateRequest(courseUpdateRequest, course);
        course = courseRepository.save(course);

        return courseMapper.toCourseResponse(course);
    }

    //Delete course
    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        courseRepository.delete(course);
    }

    //Enable course
    @Override
    public void enableCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        course.setIsDeleted(false);
        courseRepository.save(course);
    }

    //Disable course
    @Override
    public void disableCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        course.setIsDeleted(true);
        courseRepository.save(course);
    }

    //Update thumbnail status by courseId
    @Override
    public void updateThumbnail(String id, ThumbnailUpdateRequest updateThumbnail) {
        //Validate course
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        //Validate thumbnail
        if (courseRepository.existsByThumbnail(updateThumbnail.thumbnail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This thumbnail is already exist");
        }
        course.setThumbnail(updateThumbnail.thumbnail());
        courseRepository.save(course);
    }

    //Update payment status by courseId
    @Override
    public void updatePayment(String id, PaymentUpdateRequest updatePaymentRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        course.setIsPaid(updatePaymentRequest.status());
        courseRepository.save(course);
    }

    //Update visibility status by courseId
    @Override
    public void updateVisibility(String id, VisibilityUpdateRequest visibilityUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        course.setIsDrafted(visibilityUpdateRequest.status());
        courseRepository.save(course);
    }

    //Video update
    @Override
    public void updateVideo(String id, VideoUpdateRequest videoUpdateRequest) {
        //Validate course
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));
        courseMapper.fromVideoUpdateRequest(videoUpdateRequest, course);
        courseRepository.save(course);
    }

    //Create section
    @Override
    public SectionCreateRequest createSection(String courseId, SectionCreateRequest sectionCreateRequest) {
        // Validate Course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This course has not been found"));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This course has not been found"));

        // Validate Section
        Section section = sectionRepository.findByOrderNo(videoCreateRequest.sectionOrderNo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This section has not been found"));

        // Create and set up Video
        Video video = courseMapper.fromVideoCreateRequest(videoCreateRequest);
        video.setLectureNo(UUID.randomUUID().toString().substring(0, 8));
        // Associate Video with Section
        section.getVideos().add(video);
        sectionRepository.save(section); // Save the updated Section with the new Video
        // Optionally, if you need to return a modified VideoCreateRequest
        return courseMapper.toVideoCreateRequest(videoCreateRequest);
    }

    //post filter
    @Override
    public Page<?> createFilter(FilterDto filterDto, FilterResponse filterResponse, int page, int size) {

        // Build the query with filters
        Query query = FilterByRequestBody.buildQuery(filterDto, Course.class);

        // Set up pagination
        Pageable pageable = PageRequest.of(page, size);

        // Get the paginated list of courses
        List<Course> courses = mongoTemplate.find(query.with(pageable), Course.class);

        // Get the total number of matching records
        long totalRecords = mongoTemplate.count(query, Course.class);

        // Map courses to response DTOs with pagination info
        return ResponseUtil.mapCoursesResponse(courses, filterResponse, courseMapper);

    }

    @Override
    public Page<?> getFilter(String title, FilterResponse filterResponse, int page, int size) {
        // Build Criteria for filtering by title (case-insensitive search)
        Criteria criteria = Criteria.where("title").regex(title, "i");

        // Create a Query object using the Criteria
        Query query = new Query(criteria);

        // Apply pagination
        PageRequest pageRequest = PageRequest.of(page, size);
        query.with(pageRequest);

        // Execute the query using MongoTemplate
        List<Course> courses = mongoTemplate.find(query, Course.class);

        if (courses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No courses found with the specified title");
        }
        // Clone the query for count operation to avoid conflict
        Query countQuery = Query.of(query).limit(-1).skip(-1);
        long totalRecords = mongoTemplate.count(countQuery, Course.class);

        // Convert the Course entities to CourseResponse DTOs
        return ResponseUtil.mapCoursesResponse(courses, filterResponse, courseMapper);
    }
}
