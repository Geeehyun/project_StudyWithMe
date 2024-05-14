package org.fullstack4.projectstudywithme.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    @Column(length = 100, nullable = false)
    private String thumbnailPath;
    @Column(length = 100, nullable = false)
    private String thumbnail;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 1000, nullable = false)
    private String content;
    @Column(length = 50, nullable = false)
    private String memberId;
    @Column(length = 2, nullable = false)
    private String displayYn;
    @Column(length = 10, nullable = true)
    private LocalDateTime displayStartDate;
    @Column(length = 10, nullable = true)
    private LocalDateTime displayEndDate;
    @Builder.Default
    @Column(length = 11, nullable = false)
    private int likes=0;
    @Column(length = 1000, nullable = false)
    private String category;
    @Column(length = 1000, nullable = false)
    private String tags;
}
