package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.MyUser;
import org.springframework.data.repository.CrudRepository;

// получить нужные данные из БД юзеров
public interface UserRepository extends CrudRepository<MyUser, Integer> {
    MyUser findByLogin(String login);
}