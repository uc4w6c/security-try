package com.example.demo.controller;

import com.example.demo.domain.AccountUserDetails;
import com.example.demo.domain.Post;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 掲示板コントローラ
 */
@Controller("post")
public class PostController {
    @GetMapping("/")
    public List<Post> index() {
        return null;
    }

}
