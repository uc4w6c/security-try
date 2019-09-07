package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.domain.AccountUserDetails;
import com.example.demo.repository.AccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    // @Transactional(readOnly = true) なぜかエラーになる
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = Optional.ofNullable(accountRepository.findByUsername(username))
                    .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new AccountUserDetails(account, AuthorityUtils.createAuthorityList(("ROLE_USER")));
    }
}
