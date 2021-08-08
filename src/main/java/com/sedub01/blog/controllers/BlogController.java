package com.sedub01.blog.controllers;

import java.util.*;
import java.util.Optional;

import com.sedub01.blog.models.Post;
import com.sedub01.blog.repos.PostRepositiory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
        if(!postRepositiory.existsById(id))
            return "redirect:/blog";

        Optional<Post> post = postRepositiory.findById(id);
        List<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        if(!postRepositiory.existsById(id))
            return "redirect:/blog";

        Optional<Post> post = postRepositiory.findById(id);
        List<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }
    @PostMapping("/blog/{id}/edit")
    // название "title" берется из названия в blog-add.html
    public String blogPostUpdate(@PathVariable(value = "id") long id,
        @RequestParam String title,
        @RequestParam String anons,
        @RequestParam String full_text, 
        Model model){
        Post post = postRepositiory.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFulltext(full_text);
        postRepositiory.save(post);
        return "redirect:/blog";
    }
    @PostMapping("/blog/{id}/delete")
    // название "title" берется из названия в blog-add.html
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepositiory.findById(id).orElseThrow();
        postRepositiory.delete(post);
        
        return "redirect:/blog";
    }
}
