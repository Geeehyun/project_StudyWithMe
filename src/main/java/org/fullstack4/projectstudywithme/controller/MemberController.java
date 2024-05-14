package org.fullstack4.projectstudywithme.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.Common.CookieUtil;
import org.fullstack4.projectstudywithme.dto.LoginDTO;
import org.fullstack4.projectstudywithme.dto.MemberDTO;
import org.fullstack4.projectstudywithme.service.MemberServiceIf;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Log4j2
@RequestMapping(value = {"/login", "/mypage"})
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceIf memberServiceIf;
    @GetMapping("/login")
    public void getLogin() {}

    @PostMapping("/login")
    public String postLogin(LoginDTO loginDTO,
                            @RequestParam(name = "auto_login", defaultValue = "off")String auto_login,
                            HttpServletResponse response,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = memberServiceIf.login(loginDTO.getMemberId(), loginDTO.getPwd(), session);
        if(memberDTO != null) {
            if(memberDTO.getTryCount() == 0) {
                if(auto_login.equals("on")) {
                    CookieUtil.setCookies(response, 999999, "auto_login", memberDTO.getMemberId());
                }
                return "redirect:/main/main";
            } else {
                if(memberDTO.getTryCount() >= 5) {
                    redirectAttributes.addFlashAttribute("err", "5회 이상 로그인 실패로 로그인할 수 없습니다.");
                } else {
                    redirectAttributes.addFlashAttribute("err", "로그인 실패 ("+memberDTO.getTryCount()+"회/5회)");
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("err", "없는 회원입니다.");
        }
        return "redirect:/login/login";
    }
    @PostMapping("/logout")
    public String postLogout(
            @RequestParam(name = "memberId", defaultValue = "")String memberId,
            HttpSession session,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        if(!memberId.isEmpty()) {
            session.invalidate();
            CookieUtil.setDeleteCookie(response, "auto_login");
            redirectAttributes.addFlashAttribute("result", "로그아웃 성공");
        } else {
            redirectAttributes.addFlashAttribute("result", "로그아웃 실패");
        }
        return "redirect:/main/main";
    }

    @GetMapping("/join")
    public void getJoin() {}
    @PostMapping("/join")
    public String postJoin(@Valid MemberDTO memberDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("err", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("memberDTO", memberDTO);
            return "redirect:/login/join";
        }
        log.info("Controller MemberDTO : {}", memberDTO);
        int result = memberServiceIf.join(memberDTO);
        if (result > 0) {
            log.info("-----------회원가입 성공 > memberDTO" + memberDTO);
            redirectAttributes.addAttribute("memberName", memberDTO.getMemberName());
            return "redirect:/login/complete";
        } else {
            log.info("-----------회원가입 실패 > memberDTO" + memberDTO);
            redirectAttributes.addFlashAttribute("memberDTO", memberDTO);
            return "redirect:/login/join";
        }
    }
    @GetMapping("/complete")
    public void getComplete() {}
    @GetMapping("/findPwd")
    public String getFindPwd(@RequestParam(name="step", defaultValue = "1")String step,
                             @RequestParam(name = "memberId", defaultValue = "")String memberId,
                             Model model) {
        if(step.equals("1")) {
            return "/login/findPwd1";
        } else if(step.equals("2")) {
            model.addAttribute("memberId", memberId);
            return "/login/findPwd2";
        }
        return "/login/findPwd1";
    }
    @PostMapping("/findPwd")
    public String postFindPwd(@RequestParam(name="step", defaultValue = "1")String step,
                              @RequestParam(name = "memberId", defaultValue = "")String memberId,
                              @RequestParam(name = "org_pwd", defaultValue = "")String orgPwd,
                              @RequestParam(name = "new_pwd", defaultValue = "")String newPwd,
                              RedirectAttributes redirectAttributes) {
        if(step.equals("1")) {
            if(!memberId.isEmpty()){
                int result = memberServiceIf.updatePwdToTemp(memberId);
                if(result > 0) {
                    redirectAttributes.addAttribute("memberId", memberId);
                    redirectAttributes.addAttribute("step", "2");
                } else {
                    redirectAttributes.addFlashAttribute("err", "회원정보를 다시 확인하세요");
                }
            }
            return "redirect:/login/findPwd";
        } else if(step.equals("2")) {
            log.info("인생");
            if(!orgPwd.isEmpty() && !newPwd.isEmpty()) {
                log.info("memberId : {}", memberId);
                log.info("orgPwd : {}", orgPwd);
                log.info("newPwd : {}", newPwd);
                int result = memberServiceIf.updatePwdToNew(memberId, orgPwd,newPwd);
                if(result > 0) {
                    redirectAttributes.addFlashAttribute("result", "변경완료");
                    return "redirect:/login/login";
                } else {
                    redirectAttributes.addFlashAttribute("result", "변경실패");
                    redirectAttributes.addAttribute("memberId", memberId);
                    redirectAttributes.addAttribute("step", "2");
                }
            } else {
                redirectAttributes.addFlashAttribute("err", "정보를 다시 확인하세요");
                redirectAttributes.addAttribute("memberId", memberId);
                redirectAttributes.addAttribute("step", "2");
            }
        }
        return "redirect:/login/findPwd";
    }
    @GetMapping("/mypage")
    public void getMypage() {}
    @PostMapping("/mypage")
    public String postMypage(@Valid MemberDTO newMemberDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                             HttpSession session) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("err", bindingResult.getAllErrors());
        }
        log.info("newMemberDTO : {}", newMemberDTO);
        int result = memberServiceIf.updateMember(newMemberDTO);
        if (result > 0) {
            session.setAttribute("memberDTO", newMemberDTO);
            redirectAttributes.addFlashAttribute("result", "변경완료");
        } else {
            redirectAttributes.addFlashAttribute("result", "변경실패");
        }
        return "redirect:/mypage/mypage";
    }
}
