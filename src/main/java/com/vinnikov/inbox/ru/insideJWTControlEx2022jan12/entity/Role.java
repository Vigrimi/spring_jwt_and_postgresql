package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "tb_inside_roles")
@Getter
@Setter
public class Role // сущность роль
{
    @Id // айди ролей, автоинкремент
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
