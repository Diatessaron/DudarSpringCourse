package com.fazlyev.webblog.controllers;

import com.fazlyev.webblog.models.Post;
import com.fazlyev.webblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    private PostRepository repository;

    @Autowired
    public BlogController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/blog")
    public String blog(Model model){
        Iterable<Post> posts = repository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }
}
