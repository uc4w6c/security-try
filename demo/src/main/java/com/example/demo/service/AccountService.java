package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.form.UserCreateForm;
import com.example.demo.form.UserPasswordChangeForm;
import com.example.demo.repository.AccountRepository;
import org.seasar.doma.jdbc.Result;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * アカウント作成メソッド
     * TODO: formに依存しちゃってるから改善したい
     * @param form
     * @return
     */
    public Account create(UserCreateForm form) {
        String passwordDigest = passwordEncoder.encode(form.getPassword());
        String activationDigest = UUID.randomUUID().toString();
        Account account = new Account(form.getEmail(), passwordDigest, form.getBirthday(), activationDigest, false);

        Result<Account> result = accountRepository.save(account);

        // ここからメール送信。本来であれば別クラスに移動すべき
        // 出来れば非同期にしたい
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("test@localhost.com");
        msg.setTo("to@localhost.com");
        msg.setSubject("ユーザー登録");

        // 以下JavaMailSenderでHTMLメールを送るようにすること
        msg.setText("testtest");

        this.sender.send(msg);

        return result.getEntity();
    }

    /**
     * パスワード変更メソッド
     * @param email
     * @param plainPassword
     * @return void
     */
    public void passwordChange(String email, String plainPassword) {
        String passwordDigest = passwordEncoder.encode(plainPassword);
        accountRepository.passwordChange(email, passwordDigest);
    }

    public Account find(String email) {
        return accountRepository.findByEmail(email);
    }
}
