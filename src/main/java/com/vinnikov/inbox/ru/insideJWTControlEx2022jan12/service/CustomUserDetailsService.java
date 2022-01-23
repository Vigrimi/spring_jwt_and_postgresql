package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.service;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.MyUser;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // бизнес-логика
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        MyUser fromDb = userRepository.findByLogin(login);
        if (fromDb == null) {
            throw new UsernameNotFoundException("Пользователь с логином '" + login + "' не был найден.");
        }
        // юзер из секьюрити
        return User.builder()
                .username(fromDb.getLogin())
                .password(fromDb.getPassword())
                .roles(fromDb.getRole().getName().split("_")[1])
                .build();
    }
}
