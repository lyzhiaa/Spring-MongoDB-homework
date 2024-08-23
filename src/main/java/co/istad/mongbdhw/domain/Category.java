package co.istad.mongbdhw.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String uuid;
    private String name;
    private String icon;
    private Boolean isDeleted;
}
