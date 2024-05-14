package org.fullstack4.projectstudywithme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private String idx;
    @NotNull
    private String memberId;
    @NotNull
    private String memberName;
    @NotNull
    private String pwd;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    private String reg_date;
    private String modify_date;
    private String leave_date;
    @Builder.Default
    private String status="Y";
    private String auto_login;
    private int tryCount;
}
