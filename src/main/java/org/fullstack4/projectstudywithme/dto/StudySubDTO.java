package org.fullstack4.projectstudywithme.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudySubDTO extends StudyDTO {
    private String idx;
    private String studyIdx;
    private String memberId;
    private String memberName;
    private LocalDateTime regDate;
    private String regDateToString;
    @Override
    public void setDate() {
        if(regDate != null) {
            this.regDateToString = this.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
