package com.github.controllers;

import com.github.models.Role;
import com.github.models.User;
import com.github.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
//Mapping помечается на уровне класса, чтобы у каждого метода не подписывать в начале пути "/user"
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')") // только админ имеет доступ к методам этого контроллера
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String saveUser(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("user-id") User user){
        // в контроллере не должно быть никакой логики - для этого существуют сервисные классы
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    // короче, без этой аннотации всё пойдет по одному месту (см. комментарий в MainController)
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email){
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }
}
