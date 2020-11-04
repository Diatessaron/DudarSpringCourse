package com.fazlyev.webblog.controllers;

import com.fazlyev.webblog.models.Post;
import com.fazlyev.webblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,
                              @RequestParam String full_text, Model model){
        Post post = new Post(title, anons, full_text);
        post.setId(repository.count()+1);
        repository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable("id") long id, Model model){
        if(!repository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = repository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);

        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model){
        if(!repository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = repository.findById(id);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id") long id, @RequestParam String title,
                                 @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = repository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        repository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(@PathVariable("id") long id, Model model){
        Post post = repository.findById(id).orElseThrow();
        repository.delete(post);

        return "redirect:/blog";
    }
}
