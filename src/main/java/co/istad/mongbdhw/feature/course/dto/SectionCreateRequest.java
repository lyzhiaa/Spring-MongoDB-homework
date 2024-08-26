package co.istad.mongbdhw.feature.course.dto;

import co.istad.mongbdhw.domain.Video;

import java.util.List;

public record SectionCreateRequest(
        String title,
        Integer orderNo,
        List<Video> videos
) {
}
