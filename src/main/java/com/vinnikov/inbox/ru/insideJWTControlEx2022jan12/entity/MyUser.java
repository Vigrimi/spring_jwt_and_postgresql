package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_inside_users", uniqueConstraints = {@UniqueConstraint(columnNames = "login")})
@Getter
@Setter
public class MyUser // сущность пользователи
{
    @Id // айди юзеров, автоинкремент
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @NotNull(message = "Поле не может быть пустым")
    @Size(min = 3, max = 10, message = "Значения от 3 до 10")
    private String login; // name

    @NotNull(message = "Поле не может быть пустым")
    @Size(min = 6, message = "Значения от 6")
    private String password;

    @ManyToOne // у многих пользователей может быть одна и таже роль
    private Role role;

    @Override
    public String toString() {
        return "MyUser{" +
                "user_id=" + user_id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}