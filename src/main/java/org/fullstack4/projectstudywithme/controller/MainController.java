package org.fullstack4.projectstudywithme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/main")
@Controller
public class MainController {
    @GetMapping("/main")
    public void GetMain() {}
}
