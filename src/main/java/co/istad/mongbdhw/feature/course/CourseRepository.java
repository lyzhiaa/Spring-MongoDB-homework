package co.istad.mongbdhw.feature.course;

import co.istad.mongbdhw.domain.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    boolean existsByTitle(String title);
    boolean existsByThumbnail(String thumbnail);
    Optional<Course> findById(String id);

}
