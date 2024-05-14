package org.fullstack4.projectstudywithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudySubDTO {
    private String idx;
    private String studyIdx;
    private String memberId;
    private String memberName;
    private String reg_date;
}
