package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    boolean existsByTitle(String title);
    boolean existsByThumbnail(String thumbnail);
    Optional<Course> findById(String id);
    Optional<Course> findByInstructorUsername(String instructorUsername);
    Optional<Course> findBySlug(String slug);
    List<Course> findByPrice(String price);
//    Optional<Course> findByIsDrafted(Boolean isDrafted);
    List<Course> findByIsDraftedIsTrue();
    List<Course> findByIsDraftedIsFalse();
}
