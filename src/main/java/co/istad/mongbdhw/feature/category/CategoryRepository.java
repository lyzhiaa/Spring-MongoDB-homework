package co.istad.mongbdhw.feature.category;

import co.istad.mongbdhw.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, Integer> {
    boolean existsByName(String name);
    Optional<Category> findById(String id);
    Optional<Category> findByName(String name);

}
