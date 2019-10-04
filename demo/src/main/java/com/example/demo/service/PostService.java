package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.exception.NotFoundException;
import com.example.demo.form.UserCreateForm;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PasswordReissueInfoRepository;
import com.example.demo.repository.PostRepository;
import org.seasar.doma.jdbc.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<List<Account>> find(String email) {
        return postRepository.findAll();
    }
}
