package com.sedub01.blog.controllers;

import com.sedub01.blog.models.Post;
import com.sedub01.blog.repos.PostRepositiory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

    @Autowired
    private PostRepositiory postRepositiory;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepositiory.findAll();
        model.addAttribute("posts", posts); 
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }
    @PostMapping("/blog/add")
    // название "title" берется из названия в blog-add.html
    public String blogPostAdd(@RequestParam String title,
        @RequestParam String anons,
        @RequestParam String full_text, 
        Model model){
        Post post = new Post(title, anons, full_text);
        postRepositiory.save(post);
        return "redirect:/blog";
    }
}
