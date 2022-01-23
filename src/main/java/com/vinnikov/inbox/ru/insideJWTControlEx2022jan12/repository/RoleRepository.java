package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.Role;
import org.springframework.data.repository.CrudRepository;

// получить нужные данные из БД ролей
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}