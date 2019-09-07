package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.form.UserCreateForm;
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

    public Account create(UserCreateForm form) {
        String hashPassword = passwordEncoder.encode(form.getPassword());
        Account account = new Account(form.getUsername(), hashPassword);
        Result<Account> result = accountRepository.save(account);
        return result.getEntity();
    }
}
