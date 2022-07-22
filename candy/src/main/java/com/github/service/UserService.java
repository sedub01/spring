package com.github.service;

import com.github.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //это как Components, но больше как уточнение, что это сервисный класс
// Также он используется вместо аутентификации по dataSource'у
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    // В этом методе мы определяем правила загрузки пользователя по его имени
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
