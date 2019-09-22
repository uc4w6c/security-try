package com.example.demo.controller;

import com.example.demo.domain.Account;
import com.example.demo.domain.AccountUserDetails;
import com.example.demo.form.UserCreateForm;
import com.example.demo.form.UserPasswordChangeForm;
import com.example.demo.service.AccountService;
import com.example.demo.service.AccountUserDetailService;
import com.example.demo.service.SendMailService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

@RequestMapping("/")
@Controller
public class AccountController {

    private final AccountService accountService;
    private final SendMailService sendMailService;

    public AccountController(AccountService accountService, SendMailService sendMailService) {
        this.accountService = accountService;
        this.sendMailService = sendMailService;
    }

    /*
    @ModelAttribute
    public UserCreateForm setupForm() {
        return new UserCreateForm();
    }
    */

    @GetMapping("signup")
    public String create(@ModelAttribute("userCreateForm") UserCreateForm userCreateForm /*, Model model*/) {
        // model.addAttribute("userCreateForm", new UserCreateForm());
        return "account/create";
    }

    @PostMapping("signup")
    public String create(@ModelAttribute("userCreateForm") @Validated UserCreateForm userCreateForm,
                             BindingResult result/*,
                             RedirectAttributes redirectAttributes,
                             Model model*/) {
        if (result.hasErrors()) {
            // redirectAttributes.addFlashAttribute("form", form);
            // return "redirect:/signup";

            // model.addAttribute("userCreateForm", form);
            // return "account/create";
            return create(userCreateForm);
        }
        // model.addAttribute("user", userService.create(form));
        Account account = accountService.create(userCreateForm);

        Context context = new Context();

        sendMailService.sendMail();
        return "redirect:/top";
    }

    /**
     * なぜか動かない。。。_method patchが動作していないっぽい。Springのバグか？
     * NOTE: patchが動かないからPOST
     * @param form
     * @param accountUserDetails
     * @param result
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("account/edit")
    public String passwordChange(@ModelAttribute("form") @Validated UserPasswordChangeForm form,
                                 @AuthenticationPrincipal AccountUserDetails accountUserDetails,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("form", form);
            return "redirect:/account/detail";
        }
        // model.addAttribute("user", userService.create(form));
        accountService.passwordChange(accountUserDetails.getUsername(), form.getPassword());
        return "redirect:/top";
    }

    @GetMapping("account/detail")
    public String detail(@AuthenticationPrincipal AccountUserDetails accountUserDetails, Model model) {
        Account account = accountService.find(accountUserDetails.getUsername());
        model.addAttribute("account", account);
        return "account/detail";
    }
}
