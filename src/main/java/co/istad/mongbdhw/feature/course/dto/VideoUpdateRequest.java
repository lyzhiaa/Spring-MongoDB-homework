package co.istad.mongbdhw.feature.course.dto;

public record VideoUpdateRequest(
        Integer sectionOrderNumber,
        String lectureNo,
        Integer orderNo,
        String title,
        String fileName
) {
}
