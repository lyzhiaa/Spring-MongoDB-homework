package co.istad.mongbdhw.feature.course.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CourseResponse(
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
        LocalDateTime updatedAt
) {
}
