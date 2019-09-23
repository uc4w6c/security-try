package com.example.demo.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AccountUserDetails implements UserDetails {
    private final Account account;
    private final Collection<GrantedAuthority> authorities;

    public AccountUserDetails(Account account) {
        this.account = account;
        this.authorities = AuthorityUtils.createAuthorityList(("ROLE_USER"));
    }

    public String getPassword() {
        return account.getPasswordDigest();
    }
    public String getUsername() {
        return account.getEmail();
    }
    public boolean isEnabled() {
        return account.isEnabled();
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean isAccountNonExpired() {
        return true;
    }
    public boolean isAccountNonLocked() {
        return true;
    }
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Account getAccount() {
        return account;
    }
}
