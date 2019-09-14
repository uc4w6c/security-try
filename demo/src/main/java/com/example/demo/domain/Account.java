package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import java.util.Date;

@Getter
// @RequiredArgsConstructor
@AllArgsConstructor
@Entity(immutable = true, naming = NamingType.SNAKE_UPPER_CASE)
@Table(name = "accounts")
public class Account {
    @Id
    private final String email;
    private final String passwordDigest;
    private final Date birthday;
    private final String activationDigest;
    private final boolean enabled;
}
