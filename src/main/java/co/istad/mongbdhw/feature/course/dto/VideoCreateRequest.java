package co.istad.mongbdhw.feature.course.dto;

public record VideoCreateRequest(
        Integer sectionOrderNumber,
        String lectureNo,
        Integer orderNo,
        String title,
        String fileName
) {
}
