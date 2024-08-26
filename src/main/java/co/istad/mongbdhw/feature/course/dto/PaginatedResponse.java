package co.istad.mongbdhw.feature.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content;
    private PageMetadata page;

    @Data
    @AllArgsConstructor
    public static class PageMetadata {
        private int size;
        private int number;
        private long totalElements;
        private int totalPages;
    }
}
