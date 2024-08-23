package co.istad.mongbdhw.feature.course.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CourseUpdateRequest(
        String title,
        String slug,
        String description,
        String contents,
        BigDecimal price,
        Integer discount,
        @NotBlank(message = "CategoryName is required!!!")
        String categoryName,
        String code,
        String instructorUsername
) {
}
