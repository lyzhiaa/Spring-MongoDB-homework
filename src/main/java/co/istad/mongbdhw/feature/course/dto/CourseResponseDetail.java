package co.istad.mongbdhw.feature.course.dto;

import co.istad.mongbdhw.domain.Section;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CourseResponseDetail(
        String id,
        String uuid,
        String title,
        String slug,
        String description,
        BigDecimal price,
        Integer discount,
        Boolean isPaid,
        Boolean isDrafted,
        String  thumbnail,
        String contents,
        String categoryName,
        String instructorUsername,
        List<Section> sections,
        LocalDateTime updatedAt
) {
}
