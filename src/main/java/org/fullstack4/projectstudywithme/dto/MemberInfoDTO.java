package org.fullstack4.projectstudywithme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDTO {
    private String idx;
    private String memberId;
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,20}$")
    private String memberName;
    private String pwd;
    @Pattern(regexp = "^[0-9]{10,11}$")
    private String phone;
    @Email
    private String email;
    private String regDate;
    private String modifyDate;
    private String leaveDate;
    @Builder.Default
    private String status="Y";
    private String auto_login;
    private int tryCount;
    private String message;
}
