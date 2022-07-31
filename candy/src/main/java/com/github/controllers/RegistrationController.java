package com.github.controllers;

import com.github.models.User;
import com.github.service.ControllerUtils;
import com.github.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, /*для получения ошибок валидации*/
                          Model model){
        if (user.getPassword() != null && !user.getPassword().equals(user.getValidationPassword())){
            model.addAttribute("passwordError", "Пароли различаются!");
            return "registration";
        }
        if (bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)){ // если не смогли добавить такого пользователя
            model.addAttribute("usernameError", Arrays.asList("Пользователь с таким именем существует"));
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
