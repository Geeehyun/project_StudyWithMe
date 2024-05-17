package org.fullstack4.projectstudywithme.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fullstack4.projectstudywithme.Common.CommonUtil;

import java.time.LocalDate;
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
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String memberId;
    private String memberName;
    @NotNull
    private String displayYn;
    private LocalDate displayStartDate;
    private String displayStartDateToString;
    private LocalDate displayEndDate;
    private String displayEndDateToString;
    private String likes;
    @NotNull
    private String category;
    private String[] categories;
    @NotNull
    private String tags;
    private String[] tagsArr;
    private LocalDateTime regDate;
    private String regDateToString;
    private String modifyDate;
    private String message;

    public void setDate() {
        if(regDate != null) {
            this.regDateToString = this.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if(displayStartDate != null && displayEndDate != null) {
            this.displayStartDateToString = this.displayStartDate.toString();
            this.displayEndDateToString = this.displayEndDate.toString();
        }
    }
    public void setCategories() {
        if(this.category != null) {
            this.categories = category.split(",");
            for(String i : categories) {
                i.trim();
            }
        }
    }
    public void setTagsArr() {
        if(this.tags != null) {
            this.tagsArr = tags.split(",");
            for(String i : tagsArr) {
                i.trim();
            }
        }
    }
    public void setLikePlus1() {
        this.likes = String.valueOf((CommonUtil.parseInt(this.likes) + 1));
    }
}
