package org.fullstack4.projectstudywithme.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDateTime displayStartDate;
    private String displayStartDateToString;
    private LocalDateTime displayEndDate;
    private String displayEndDateToString;
    private String likes;
    private String category;
    private String[] categories;
    private String tags;
    private LocalDateTime regDate;
    private String regDateToString;
    private String modifyDate;
    private String message;

    public void setDate() {
        if(displayStartDate != null && displayEndDate != null && regDate != null) {
            this.displayStartDateToString = this.displayStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.displayEndDateToString = this.displayEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.regDateToString = this.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
    public void setCategories() {
        if(category != null) {
            this.categories = category.split(",");
            for(String i : categories) {
                i.trim();
            }
        }
    }
}
