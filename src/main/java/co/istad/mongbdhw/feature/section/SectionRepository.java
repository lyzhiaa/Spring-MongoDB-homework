package co.istad.mongbdhw.feature.section;

import co.istad.mongbdhw.domain.Section;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SectionRepository extends MongoRepository<Section, String> {
    Optional<Section> findByOrderNo(Integer orderNo);

}
