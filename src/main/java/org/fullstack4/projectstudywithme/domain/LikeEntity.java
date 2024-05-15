package org.fullstack4.projectstudywithme.domain;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_like")
public class LikeEntity extends BaseEntity {
    @Column(length = 11, unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    @Column(length = 11, nullable = false)
    private int studyIdx;
    @Column(length = 50, nullable = false)
    private String memberId;
    @Column(length = 100, nullable = false)
    private String memberName;
}
