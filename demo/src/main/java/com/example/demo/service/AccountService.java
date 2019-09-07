package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.form.UserCreateForm;
import com.example.demo.form.UserPasswordChangeForm;
import com.example.demo.repository.AccountRepository;
import org.seasar.doma.jdbc.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        String encryptPassword = passwordEncoder.encode(form.getPassword());
        Account account = new Account(form.getEmail(), encryptPassword, form.getBirthday(), false);
        Result<Account> result = accountRepository.save(account);
        return result.getEntity();
    }

    /**
     * パスワード変更メソッド
     * @param email
     * @param plainPassword
     * @return void
     */
    public void passwordChange(String email, String plainPassword) {
        String encryptPassword = passwordEncoder.encode(plainPassword);
        accountRepository.passwordChange(email, encryptPassword);
    }

    public Account find(String email) {
        return accountRepository.findByEmail(email);
    }
}
