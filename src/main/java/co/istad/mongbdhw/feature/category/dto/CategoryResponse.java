package co.istad.mongbdhw.feature.category.dto;

public record CategoryResponse(
        String id,
        String uuid,
        String name,
        String icon
) {
}
