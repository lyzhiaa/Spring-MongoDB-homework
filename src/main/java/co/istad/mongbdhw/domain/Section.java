package co.istad.mongbdhw.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "sections")
public class Section {
    @Id
    private Integer orderNo;
    private String title;
    private List<Video> videos;
}
