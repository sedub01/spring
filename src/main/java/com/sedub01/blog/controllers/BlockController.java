package com.sedub01.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlockController {
    @GetMapping("/blog")
    public String blogMain(Model model){
        return "blog-main";
    }
}
