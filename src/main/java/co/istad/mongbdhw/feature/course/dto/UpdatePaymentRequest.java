package co.istad.mongbdhw.feature.course.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePaymentRequest(
        @NotBlank(message = "Status is Require!!!")
        Boolean status
) {
}
