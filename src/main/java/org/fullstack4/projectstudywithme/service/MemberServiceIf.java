package org.fullstack4.projectstudywithme.service;

import jakarta.servlet.http.HttpSession;
import org.fullstack4.projectstudywithme.dto.MemberDTO;

public interface MemberServiceIf {
    MemberDTO login(String memberId, String pwd, HttpSession session);
    MemberDTO autoLogin(String memberId, HttpSession session);
    int join(MemberDTO memberDTO);
    int updatePwdToTemp(String memberId);
    int updatePwdToNew(String memberId, String oldPwd, String newPwd);
    int updateMember(MemberDTO newMemberDTO);
    int leaveMember(String memberID);
}
