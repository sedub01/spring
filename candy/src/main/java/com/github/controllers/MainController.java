package com.github.controllers;

import com.github.models.Message;
import com.github.models.User;
import com.github.repos.MessageRepo;
import com.github.service.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        Iterable<Message> messages = (!filter.isEmpty())?
                messageRepo.findByTag(filter) : messageRepo.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "index";
    }

    @PostMapping("/index")
    public String addMessage(
            //эта аннотация нужна для внедрения текущего (зарегистрированного) пользователя!
            @AuthenticationPrincipal User user,
            @Valid Message message, //аннотация запускает валидацию поля
            BindingResult bindingResult, //здесь отображаются ошибки валидации
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        }
        else{
            uploadFile(message, file);
            messageRepo.save(message);
        }
        model.addAttribute("message", null); //после этого из модели нужно наш message, иначе после добавления
                                    // мы опять получим открытую форму с сообщением, которое пытались добавить

        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages); //вот здесь именно строка messages передается в index.html
        return "index";
    }



    private void uploadFile(Message message, MultipartFile file) throws IOException {
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString(); // это нужно для предотвращения коллизий
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }
}