package org.fullstack4.projectstudywithme.controller;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequestMapping(value = {"/login", "/mypage"})
@Controller
public class MemberController {
    @GetMapping("/login")
    public void getLogin() {}

    @PostMapping("/login")
    public void postLogin(LoginDTO loginDTO) {
        log.info("==================================================");
        log.info("LoginDTO : {}", loginDTO);
    }

    @GetMapping("/join")
    public void getJoin() {}
    @PostMapping("/join")
    public void postJoin() {}
    @GetMapping("/findPwd")
    public String getFindPwd(@RequestParam(name="step", defaultValue = "1")String step) {
        if(step.equals("1")) {
            return "/login/findPwd1";
        } else if(step.equals("2")) {
            return "/login/findPwd2";
        }
        return null;
    }
    @PostMapping("/findPwd")
    public void postFindPwd() {}
    @GetMapping("/mypage")
    public void getMypage() {}
    @PostMapping("/mypage")
    public void postMypage() {}
}
