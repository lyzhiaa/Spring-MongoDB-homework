package co.istad.mongbdhw.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Video {
    private Integer orderNo;
    private String lectureNo;
    private String title;
    private String fileName;

}
