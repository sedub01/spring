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

import java.util.*;
import java.util.stream.Collectors;

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
        sendActivationMessage(user);
        return true;
    }

    private void sendActivationMessage(User user) {
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

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        //список ролей у конкретного пользователя
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        // берем все существующие в приложении роли,
        // преобразуем массив этих ролей в стрим, где
        // получаем имена ролей и полученый список имён складываем в set
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged){
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!StringUtils.isEmpty(password)){
            user.setPassword(password);
        }

        if (isEmailChanged) sendActivationMessage(user);
        userRepo.save(user); // в уроке эти две строки поменяны местами
    }
}
