package co.istad.mongbdhw.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "courses")
public class Course {
    @Id
    private String id;
    private String uuid;
    private String title;
    private String slug;
    private String description;
    private String thumbnail;
    private String contents;
    private String price;
    private Integer discount = 0;
    private String categoryName;

    private List<Section> sections;

    private String code;
    private String instructorUsername;

    private Boolean isPaid;
    private Boolean isDrafted;
    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
