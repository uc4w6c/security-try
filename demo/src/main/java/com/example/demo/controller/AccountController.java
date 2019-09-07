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

@RequestMapping("/account")
@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String create() {
        return "account/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("form") @Validated UserCreateForm form,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);
            return "redirect:/account";
        }
        // model.addAttribute("user", userService.create(form));
        userService.create(form);
        return "top";
    }
}
