package com.github.controllers;

import com.github.models.Message;
import com.github.models.User;
import com.github.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String hello(Model model) {
        return "hello";
    }

    @GetMapping("/index")
    public String mainPage(Model model){
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);
        return "index";
    }

    @PostMapping("index")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model){
        Message message = new Message(text, tag, user);

        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages); //вот здесь именно строка messages передается в index.html
        return "index";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Model model){
        List<Message> messages = messageRepo.findByTag(filter);

        model.addAttribute("messages", messages);

        return "index";
    }
}