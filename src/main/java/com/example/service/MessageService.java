package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService implements MessageInterfaceService {


    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessageById(int mesgID) {
        Optional<Message>msg = messageRepository.findById(mesgID);
        if(msg.isPresent()){
            return msg.get();
        }
        return null;
         
    }

    @Override
    public void delMessageByID(int id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Message updateMessageByID(int message_id, Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByUserID(int UserID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesByUserID'");
    }
}
