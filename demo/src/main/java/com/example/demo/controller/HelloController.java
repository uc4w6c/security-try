package com.example.demo.controller;

import com.example.demo.domain.AccountUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/top")
    public String top(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof AccountUserDetails) {
            email = ((AccountUserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        model.addAttribute("email", email);
        return "top";
    }
}
