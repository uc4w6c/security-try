package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.form.UserCreateForm;
import com.example.demo.form.UserPasswordChangeForm;
import com.example.demo.repository.AccountRepository;
import org.seasar.doma.jdbc.Result;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * アカウント仮登録メソッド
     * TODO: formに依存しちゃってるから改善したい
     * @param form
     * @return
     */
    @Transactional // 一旦ここにトランザクション追加
    public Account create(UserCreateForm form) {
        String passwordDigest = passwordEncoder.encode(form.getPassword());
        String activationDigest = UUID.randomUUID().toString();
        Account account = new Account(form.getEmail(), passwordDigest, form.getBirthday(), activationDigest, false);

        Result<Account> result = accountRepository.save(account);

        return result.getEntity();
    }

    /**
     * アカウント有効化tokenからユーザーを取得
     * @param activationDigest
     * @return
     */
    public Account accountEnable(String activationDigest) {
        Account disableAccount = accountRepository.findByActivationDigest(activationDigest);
        // TODO: ここにデータ存在しない場合の処理を入れること

        accountRepository.enableAccount(disableAccount.getEmail());
        return disableAccount;
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
