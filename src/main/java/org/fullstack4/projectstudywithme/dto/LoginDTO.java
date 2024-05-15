package org.fullstack4.projectstudywithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    private String idx;
    private String memberId;
    private String memberName;
    private String pwd;
    private String phone;
    private String email;
    private String regDate;
    private String modifyDate;
    private String leaveDate;
    private String status;
    private String auto_login;
}
