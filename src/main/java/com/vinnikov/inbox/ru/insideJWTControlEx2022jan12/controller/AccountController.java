package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.controller;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.config.jwt.JwtProvider;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.MessageMyUser;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.MyUser;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.pojo.*;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository.MessageRepository;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.service.MessageService;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

// формирование REST
@RestController
@RequestMapping("/account")
public class AccountController {
    private UserService userService;
    private JwtProvider jwtProvider;
    private MessageRepository messageRepository;
    private MessageService messageService;

    @Autowired
    public AccountController(UserService userService, JwtProvider jwtProvider, MessageRepository messageRepository,
                             MessageService messageService)
    {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    // http://localhost:8090/account/registration
    /*{
        "login":"user11",
        "password":"password11",
        "role_id":"1"
    }*/
    @PostMapping("/registration") // signup регистрация нового пользователя
    public ResponseEntity<?> registerUser(@RequestBody @Valid MyUser user)
    {
        try { // внесение нового пользователя в базу
            userService.saveUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
        // вернуть номер айди юзера из БД, если ок при регистрации
        return ResponseEntity.ok(new JwtResponseAuthorized(user.getLogin(),
              "ок, новый user зарегистрирован под номером: " + user.getUser_id() + " -> " + LocalDateTime.now()));
    }

    @PostMapping("/authorization") // signin вход уже зарегистрированного пользователя
    public ResponseEntity<?> authUser(@RequestBody MyUserInputLgnPsw user)
    {
        MyUser fromDb;
        try {
            fromDb = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
            System.out.println("********fromDb.getUser_id():" + fromDb.getUser_id());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
        // если такой пользователь найден в БД, то создаём токен
        String token = jwtProvider.generateToken(fromDb.getLogin());
        System.out.println("---------токен при авторизации:" + token);
        return ResponseEntity.ok(new JwtResponseToken(token));
    }

    @GetMapping("/admin/request") // проверка активности токена и его пользователя
    public ResponseEntity<String> tstAdmin() {
        return ResponseEntity.ok("ок, admin авторизован: " + LocalDateTime.now());
    }

    @GetMapping("/user/request") // проверка активности токена и его пользователя
    public ResponseEntity<?> tstUser(Principal principal)
    {
        System.out.println("-----90principal.:" + principal);
        return ResponseEntity.ok(new JwtResponseAuthorized(
                principal.getName(),
                "ок, user авторизован: " + LocalDateTime.now()));
    }

    /*{
        "user_name":"user25",
        "message_my_user":"test msg19_5"
    }*/
    // обработка текстов запросов от пользователя
    @PostMapping("/user/message-handler")
    public ResponseEntity<?> messagingProcessing(@RequestBody @Valid MessageMyUser message_my_user)
    {
        // проверка текста в запросе: Если пришло сообщение вида: "history 10"
        // отправить отправителю 10 последних сообщений из БД
            if(message_my_user.getMessage_my_user().equalsIgnoreCase("history 10"))
            {
                // собрать последние 10 сообщений
                long qtyMsgsInDataBase = messageRepository.count();
                System.out.println("---------начало сохранить последние 10 сообщений");
                String last10MsgsFmDb = messageService.getLast10MsgsFm(qtyMsgsInDataBase);
                System.out.println("---------конец сохранить последние 10 сообщений");
                return ResponseEntity.ok(new JwtResponseHistory10(last10MsgsFmDb));
            }
            else
            { // полученное сообщение сохранить в БД
                messageRepository.save(message_my_user);
                return ResponseEntity.ok(new JwtResponseNotHistory10("сообщение -=" +
                        message_my_user.getMessage_my_user() + "=- сохранено в базу: " + LocalDateTime.now()));
            }
    }
}
