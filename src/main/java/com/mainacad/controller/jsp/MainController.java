package com.mainacad.controller.jsp;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Profile("jsp")
public class MainController {

    @GetMapping()
    public String getLandingPage(Model model) {
        model.addAttribute("message", "Hello! Еhis is landing page!");
        return "authorization";
    }
}
