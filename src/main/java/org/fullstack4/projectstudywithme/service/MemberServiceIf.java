package org.fullstack4.projectstudywithme.service;

import jakarta.servlet.http.HttpSession;
import org.fullstack4.projectstudywithme.dto.MemberDTO;

import java.util.List;

public interface MemberServiceIf {
    MemberDTO login(String memberId, String pwd, HttpSession session);
    MemberDTO autoLogin(String memberId, HttpSession session);
    int join(MemberDTO memberDTO);
    int updatePwdToTemp(String memberId);
    int updatePwdToNew(String memberId, String oldPwd, String newPwd);
    int updateMember(MemberDTO newMemberDTO);
    int leaveMember(String memberId);
    int checkId(String memberId);
    int checkEmail(String email);
    MemberDTO selectMember(String memberId);
    List<MemberDTO> memberList(String memberIdOrName);
}
