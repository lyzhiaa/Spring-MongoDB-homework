package co.istad.mongbdhw.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    private Integer orderNo;
    private String lectureNo;
    private String title;
    private String filename;
}
