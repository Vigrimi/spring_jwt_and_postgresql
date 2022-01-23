package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.service;

import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.entity.MessageMyUser;
import com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // бизнес-логика
public class MessageService
{
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // собрать 10 последних сообщений из БД
    public String getLast10MsgsFm(long totalQtyMsgs)
    {
        long tenMsgs = 10L;
        if(totalQtyMsgs < tenMsgs)
        {
            tenMsgs = totalQtyMsgs;
        }
        String textLast10MsgsFmDB = "";
        for (long i = totalQtyMsgs; i > (totalQtyMsgs - tenMsgs); i--)
        {
            int id = (int) i;
            MessageMyUser msg = messageRepository.findById(id);
            textLast10MsgsFmDB = textLast10MsgsFmDB + msg.getMessage_my_user() + ";";
        }

        return textLast10MsgsFmDB;
    }
}
