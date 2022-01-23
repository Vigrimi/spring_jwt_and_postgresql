package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseAuthorized
{
    private String login;
    private String status;

    public JwtResponseAuthorized(String login, String status) {
        this.login = login;
        this.status = status;
    }
}
