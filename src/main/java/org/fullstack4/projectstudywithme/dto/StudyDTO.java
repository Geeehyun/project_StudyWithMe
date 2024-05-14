package org.fullstack4.projectstudywithme.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyDTO {
    private String idx;
    private String thumbnailPath;
    private String thumbnail;
    private String title;
    private String content;
    private String memberId;
    private String memberName;
    private String displayYn;
    private String displayStartDate;
    private String displayEndDate;
    private String like;
    private String category;
    private String tags;
    private String reg_date;
    private String modify_date;
}
