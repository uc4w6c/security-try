package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.domain.Post;
import com.example.demo.exception.NotFoundException;
import com.example.demo.form.PostCreateForm;
import com.example.demo.form.UserCreateForm;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PasswordReissueInfoRepository;
import com.example.demo.repository.PostRepository;
import org.seasar.doma.jdbc.Result;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final JdbcTemplate jdbcTemplate;

    public PostService(PostRepository postRepository, JdbcTemplate jdbcTemplate) {
        this.postRepository = postRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByBody(String queryBody) {
        StringBuilder sql = new StringBuilder();
        sql.append("select id, email, body, created_at ");
        sql.append("from posts ");
        sql.append("where body like '%").append(queryBody).append("%'");

        // SQLインジェクションのテスト
        List<Post> posts = new ArrayList<>();
        jdbcTemplate.query(sql.toString(),
            new BeanPropertyRowMapper<Post>(Post.class))
                .stream().forEach(n -> {
                    Post post = new Post(n.getId(), n.getEmail(),
                                         n.getBody(), n.getCreatedAt());
                    posts.add(post);
                });

        return posts;
        // DOMAだとSQLインジェクションはできなそう。いいね。
        // SQLプロセッサーを使えばいけるっぽいけど、めんどくさそう。
        // return postRepository.findByBody(query);
    }

    public Post save(String email, String body) {

        Post post = new Post(-1, email, body, LocalDate.now());
        return postRepository.save(post).getEntity();
    }
}
