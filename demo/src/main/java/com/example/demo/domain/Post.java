package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Entity(immutable = true, naming = NamingType.SNAKE_UPPER_CASE)
@Table(name = "posts")
public class Post {
    @Id
    private final int id;
    private final String email;
    private final String body;
    private final LocalDate cratedAt;
}
