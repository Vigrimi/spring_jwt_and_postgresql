package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.service;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.MyUser;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.Role;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository.RoleRepository;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // бизнес-логика
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository
            , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MyUser saveUser(MyUser user) throws Exception {
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new Exception("Пользователь с таким именем уже существует");
        }
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public MyUser findByLoginAndPassword(String login, String password) throws BadCredentialsException {
        MyUser user = userRepository.findByLogin(login);
        if (user == null) {
            throw new BadCredentialsException("Ошибка ввода логина");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Ошибка ввода пароля");
        }
        return user;
    }
}
