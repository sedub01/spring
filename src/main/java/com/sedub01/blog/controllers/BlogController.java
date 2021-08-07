package com.sedub01.blog.controllers;

import com.sedub01.blog.models.Post;
import com.sedub01.blog.repos.PostRepositiory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
