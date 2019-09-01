package com.example.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.seasar.doma.Entity;

/**
 * TODO: 実装すること
 */
@Entity
@Getter
@RequiredArgsConstructor
public class Account {
    private final String username;
    private final String password;
    private final boolean enabled;
}
