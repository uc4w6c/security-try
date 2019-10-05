package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDate;

/**
 * MEMO: SQLインジェクションテストのためにあえてimmutableじゃなくする
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(immutable = true, naming = NamingType.SNAKE_UPPER_CASE)
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String body;
    private LocalDate createdAt;
}
