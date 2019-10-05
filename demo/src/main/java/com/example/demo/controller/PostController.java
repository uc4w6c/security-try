package com.example.demo.controller;

import com.example.demo.domain.AccountUserDetails;
import com.example.demo.domain.Post;
import com.example.demo.form.PostCreateForm;
import com.example.demo.form.UserCreateForm;
import com.example.demo.service.PostService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 掲示板コントローラ
 */
@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("form") @Validated PostCreateForm postCreateForm,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("posts", posts);
            return index(model);
        }
        postService.save();

        return index(model);
    }
}
