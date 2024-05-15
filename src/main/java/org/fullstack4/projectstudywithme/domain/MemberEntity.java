package org.fullstack4.projectstudywithme.domain;

import jakarta.persistence.*;
import lombok.*;
import org.fullstack4.projectstudywithme.domain.BaseEntity;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_member")
public class MemberEntity extends BaseEntity {
    @Column(length = 11, unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    @Column(length = 50, unique = true, nullable = false)
    private String memberId;
    @Column(length = 100, nullable = false)
    private String memberName;
    @Column(length = 100, nullable = false)
    private String pwd;
    @Column(length = 20, nullable = false)
    private String phone;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 10, nullable = true)
    private LocalDateTime leave_date;
    @Column(length = 2, nullable = false)
    private String status;
    @Column(length = 11, nullable = false)
    @Builder.Default
    private int tryCount=0;

    public void modify(String memberName, String pwd, String phone, String email) {
        this.memberName = memberName;
        this.pwd = pwd;
        this.phone = phone;
        this.email = email;
        super.setModifyDate(LocalDateTime.now());
    }
}
