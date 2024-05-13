package org.fullstack4.projectstudywithme.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@RequestMapping("/mystudy")
@Controller
public class MyStudyController {
    @GetMapping("/list")
    public void getList() {}
    @GetMapping("/view")
    public void getView() {}
    @GetMapping("/regist")
    public void getRegist() {}
    @GetMapping("/modify")
    public void getModify() {}
}
