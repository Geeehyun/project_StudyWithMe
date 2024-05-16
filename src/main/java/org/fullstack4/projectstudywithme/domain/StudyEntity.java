package org.fullstack4.projectstudywithme.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SpringBootApplication
@Table(name = "tbl_study")
public class StudyEntity extends BaseEntity {
    @Column(length = 11, unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    @Column(length = 100, nullable = true)
    @Builder.Default
    private String thumbnailPath="/upload/";
    @Column(length = 100, nullable = true)
    @Builder.Default
    private String thumbnail="default.jpg";
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 1000, nullable = false)
    private String content;
    @Column(length = 50, nullable = false)
    private String memberId;
    @Column(length = 100, nullable = false)
    private String memberName;
    @Column(length = 2, nullable = false)
    private String displayYn;
    @Column(length = 10, nullable = true)
    private LocalDate displayStartDate;
    @Column(length = 10, nullable = true)
    private LocalDate displayEndDate;
    @Builder.Default
    @Column(length = 11, nullable = false)
    private int likes=0;
    @Column(length = 1000, nullable = false)
    private String category;
    @Column(length = 1000, nullable = false)
    private String tags;
}
