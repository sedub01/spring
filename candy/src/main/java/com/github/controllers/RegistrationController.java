package com.github.controllers;

import com.github.models.User;
import com.github.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        if (!userService.addUser(user)){ // если не смогли добавить такого пользователя
            model.addAttribute("message", "Пользователь с таким именем существует");
            return "registration";
        }
//        if (!user.isActive()){ //если пользователь пока что не активирован (этот блок выполняется всегда)
//            model.addAttribute("message", "Вам на почту пришла ссылка с кодом активации\n" +
//                    "Можете закрывать эту страницу");
//            return "login";
//        }
        return "redirect:/login";
        // как я понял, редирект нужен для того, чтобы избежать повторной отправки данных при обновлении страницы,
        // но мне нужно отправить задокументированный текст сверху, а с редиректом этого не сделать
    }

    //мы переходим сюда после того, как пользователь получил activation code
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        model.addAttribute("message", (isActivated)? "Пользователь активирован":
                "Код активации не найден (или устарел)!");

        return "login";
    }
}
