package co.istad.mongbdhw.feature.course.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateThumbnailRequest(
        @NotBlank(message = "Thumbnail is required!!!")
        String thumbnail
) {
}
