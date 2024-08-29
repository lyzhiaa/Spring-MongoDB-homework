package co.istad.mongbdhw.feature.category;

import co.istad.mongbdhw.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;


@EnableMongoRepositories(basePackages = "co.istad.mongbdhw.feature.category")
public interface CategoryRepository extends MongoRepository<Category, Integer> {
    boolean existsByName(String name);
    Optional<Category> findById(String id);
    Optional<Category> findByName(String name);

}
