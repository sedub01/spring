package com.github.service;

import com.github.models.Role;
import com.github.models.User;
import com.github.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service //это как Components, но больше как уточнение, что это сервисный класс
// Также он используется вместо аутентификации по dataSource'у
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SmtpMailSender smtpMailSender;

    @Override
    // В этом методе мы определяем правила загрузки пользователя по его имени
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDB = userRepo.findByUsername(user.getUsername());

        if (userFromDB != null) // если пользователь найден
            return false;

        user.setActive(false);
        //user.setActive(true); //нужно сделать так, чтобы пользователь активировался после активации, а не сразу
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user); //надо ли удалять или подождать до активации?
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Приветствую, %s! \n" +
                            "Добро пожаловать в сервис микроблогов Candy. Пожалуйста, посетите ссылку: " +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            smtpMailSender.send(user.getEmail(), " Код активации", message);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) //если активация не удалась
            return false;
        user.setActivationCode(null); //пользователь подтвержден
        user.setActive(true);
        userRepo.save(user);
        return true;
    }
}
