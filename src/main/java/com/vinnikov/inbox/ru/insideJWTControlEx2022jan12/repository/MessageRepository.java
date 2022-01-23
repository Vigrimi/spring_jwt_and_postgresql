package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.MessageMyUser;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

// получить нужные данные из БД сообщений
public interface MessageRepository extends CrudRepository<MessageMyUser, Integer>
{
    MessageMyUser findById(int id);
    ArrayList<MessageMyUser> findAll();
}