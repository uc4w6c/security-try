package com.example.demo.controller;

import com.example.demo.domain.Account;
import com.example.demo.domain.AccountUserDetails;
import com.example.demo.domain.MailSender;
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

@RequestMapping("/")
@Controller
public class AccountController {

    private final AccountService accountService;
    private final SendMailService sendMailService;

    public AccountController(AccountService accountService, SendMailService sendMailService) {
        this.accountService = accountService;
        this.sendMailService = sendMailService;
    }

    @GetMapping("signup")
    public String create(@ModelAttribute("userCreateForm") UserCreateForm userCreateForm) {
        return "account/create";
    }

    @PostMapping("signup")
    public String create(@ModelAttribute("userCreateForm") @Validated UserCreateForm userCreateForm,
                             BindingResult result) {
        if (result.hasErrors()) {
            return create(userCreateForm);
        }

        Account account = accountService.create(userCreateForm);
        String url = "/account_activation/" + account.getActivationDigest();
        MailSender mailSender = new MailSender.Builder()
                                            // .toEmail(account.getEmail())
                                            .toEmail("to@localhost.com")
                                            .subject("メールアドレス確認")
                                            .context("url", url)
                                            .templateName("account/createmail")
                                            .build();
        sendMailService.sendMail(mailSender);
        return "/account/tempregistration";
    }

    @GetMapping("account_activation/{activation_digest}")
    public String accountActivation(@PathVariable("activation_digest") String activationDigest) {

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
