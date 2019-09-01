package com.example.demo.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccoutUserDetails extends User {

    private final Account account;

    public AccoutUserDetails(Account account, boolean accountNonExpired,
                    boolean credentialsNonExpired, boolean accountNonLocked,
                    Collection<GrantedAuthority> authorities) {
        super(account.getUsername(), account.getPassword(),
                account.isEnabled(), true, true, true, authorities);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
