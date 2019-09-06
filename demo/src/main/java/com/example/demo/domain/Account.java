package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * TODO: 実装すること
 */
@Getter
@RequiredArgsConstructor
@Entity(naming = NamingType.SNAKE_UPPER_CASE, immutable = true)
@Table(name = "accounts")
public class Account {
    @Id
    private final String username;
    private final String password;
    // private final boolean enabled;
}
