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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
        sql.append("where ");
        sql.append("deleted_at is null ");
        sql.append("and body like '%").append(queryBody).append("%'");
        // SQLインジェクション対策は以下
        // sql.append("and body like ?");

        // SQLインジェクションのテスト
        List<Post> posts = new ArrayList<>();
        jdbcTemplate.query(sql.toString(),
            new BeanPropertyRowMapper<Post>(Post.class))
            // SQLインジェクション対策は以下
            // new BeanPropertyRowMapper<Post>(Post.class),
            //  "%" + queryBody + "%")
                .stream().forEach(n -> {
                    Post post = new Post(n.getId(), n.getEmail(),
                                         n.getBody(), n.getDeletedAt(),
                                         n.getCreatedAt());
                    posts.add(post);
                });

        return posts;
        // DOMAだとSQLインジェクションはできなそう。いいね。
        // SQLプロセッサーを使えばいけるっぽいけど、めんどくさそう。
        // return postRepository.findByBody(query);
    }

    public Post save(String email, String body) {

        Post post = new Post(-1, email, body, null, LocalDate.now());
        return postRepository.save(post).getEntity();
    }
}
