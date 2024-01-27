package com.example.service;

import java.util.List;

import com.example.entity.Message;

public interface MessageInterfaceService {
    Message createMessage(Message message);
    List<Message> getAllMessages();
    Message getMessageById(int mesgID);
    void delMessageByID(int id);
    Message updateMessageByID(int message_id, Message message);
    List<Message>getMessagesByUserID(int UserID);
}
