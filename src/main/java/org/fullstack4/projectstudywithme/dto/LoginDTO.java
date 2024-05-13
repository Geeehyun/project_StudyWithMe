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
    private String member_idx;
    private String member_id;
    private String member_name;
    private String pwd;
    private String phone;
    private String email;
    private String reg_date;
    private String modify_date;
    private String leave_date;
    private String status;
    private String auto_login;
}
