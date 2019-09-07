package com.example.demo.controller;

import com.example.demo.form.UserCreateForm;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/login")
@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index() {
        return "user/login";
    }

    @GetMapping("create")
    public String create() {
        return "user/create";
    }

    @PostMapping("create")
    public String create(@ModelAttribute("form") @Validated UserCreateForm form,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);
            return "redirect:/user/create";
        }
        // model.addAttribute("user", userService.create(form));
        userService.create(form);
        return "top";
    }
}
