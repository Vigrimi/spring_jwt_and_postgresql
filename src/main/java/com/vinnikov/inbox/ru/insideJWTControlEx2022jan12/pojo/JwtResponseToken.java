package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseToken
{
    private String token;

    // авторизован, токен присвоен
    public JwtResponseToken(String token) {
        this.token = token;
    }

}
