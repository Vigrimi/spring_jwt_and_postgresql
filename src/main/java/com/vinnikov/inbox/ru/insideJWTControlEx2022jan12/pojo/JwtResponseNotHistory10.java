package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseNotHistory10
{
    private String messageResult;

    public JwtResponseNotHistory10(String messageResult) {
        this.messageResult = messageResult;
    }
}
