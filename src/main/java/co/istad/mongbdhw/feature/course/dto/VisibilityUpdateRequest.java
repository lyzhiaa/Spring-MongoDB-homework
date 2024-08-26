package co.istad.mongbdhw.feature.course.dto;

import jakarta.validation.constraints.NotBlank;

public record VisibilityUpdateRequest(
        @NotBlank(message = "Status is require!!!")
        Boolean status
) {
}
