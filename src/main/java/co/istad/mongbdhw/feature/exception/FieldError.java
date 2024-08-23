package co.istad.mongbdhw.feature.exception;

import lombok.Builder;

@Builder
public record FieldError(
        String field,
        String detail
) {
}
