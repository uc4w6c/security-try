package com.example.demo.controller;

import com.example.demo.domain.MailSender;
import com.example.demo.domain.PasswordReissueInfo;
import com.example.demo.form.UserPasswordChangeForm;
import com.example.demo.form.UserPasswordReissueForm;
import com.example.demo.service.AccountReissueService;
import com.example.demo.service.AccountService;
import com.example.demo.service.SendMailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/reissue/")
@Controller
public class AccountReissueController {

    private final AccountService accountService;
    private final AccountReissueService accountReissueService;
    private final SendMailService sendMailService;

    public AccountReissueController(AccountService accountService,
                                    AccountReissueService accountReissueService,
                                    SendMailService sendMailService) {
        this.accountService = accountService;
        this.accountReissueService = accountReissueService;
        this.sendMailService = sendMailService;
    }


    @GetMapping("create")
    public String reissueCreate(@ModelAttribute("userPasswordReissueForm") UserPasswordReissueForm userPasswordReissueForm) {
        return "account/reissuecreate";
    }

    /**
     * パスワード再発行申請処理
     *
     * @param userPasswordReissueForm
     * @param result
     * @return
     */
    @PostMapping("create")
    public String reissueCreate(@ModelAttribute("userPasswordReissueForm") UserPasswordReissueForm userPasswordReissueForm,
                                BindingResult result) {

        if (result.hasErrors()) {
            return reissueCreate(userPasswordReissueForm);
        }

        PasswordReissueInfo passwordReissueInfo = accountReissueService.reissueCreate(
                userPasswordReissueForm.getEmail(),
                userPasswordReissueForm.getBirthday());

        String url = "/reissue/resetpassword/?token=" + passwordReissueInfo.getToken();
        MailSender mailSender = new MailSender.Builder()
                .toEmail(userPasswordReissueForm.getSendEmail())
                .subject("パスワード再発行")
                .context("url", url)
                .templateName("account/reissuemail")
                .build();
        sendMailService.sendMail(mailSender);

        return "account/tempreissue";
    }

    @GetMapping("resetpassword")
    public String resetpassword(@RequestParam("token") String token,
                                @ModelAttribute("userPasswordChangeForm") UserPasswordChangeForm userPasswordChangeForm,
                                Model model) {

        PasswordReissueInfo passwordReissueInfo = accountReissueService.findReissueInfo(token);
        model.addAttribute("token", token);
        model.addAttribute("email", passwordReissueInfo.getEmail());
        return "account/resetpassword";
    }

    @PostMapping("resetpassword")
    public String resetpassword(@RequestParam("token") String token,
                                @ModelAttribute("userPasswordChangeForm") @Validated UserPasswordChangeForm userPasswordChangeForm,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                BindingResult result) {

        if (result.hasErrors()) {
            return resetpassword(token, userPasswordChangeForm, model);
        }

        PasswordReissueInfo passwordReissueInfo = accountReissueService.findReissueInfo(token);
        accountService.passwordChange(passwordReissueInfo.getEmail(), userPasswordChangeForm.getPassword());

        // TODO: 余裕があればメッセージを表示できるようにする
        // redirectAttributes.addFlashAttribute("msg", "パスワードをリセットしました。ログインしてください。");
        return "redirect:/login";
    }
}
