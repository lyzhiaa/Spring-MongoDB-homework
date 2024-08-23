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
public class Section {
    private String title;
    private Integer orderNo;
    private List<Video> videos;
}
