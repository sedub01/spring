package com.github.controllers;

import com.github.models.Message;
import com.github.models.User;
import com.github.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @Value("${upload.path}") // аннотация позволяет получить переменную из .properties файла
    private String uploadPath; // и вставляет в эту переменную

    @GetMapping("/")
    public String hello(Model model) {
        return "hello";
    }

    @GetMapping("/index")
    public String mainPage(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<Message> messages = messageRepo.findAll();
        if (filter != null)
            messages = messageRepo.findByTag(filter);
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("index")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        Message message = new Message(text, tag, user);
        uploadFile(message, file);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages); //вот здесь именно строка messages передается в index.html
        return "index";
    }

    private void uploadFile(Message message, MultipartFile file) throws IOException {
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }
}