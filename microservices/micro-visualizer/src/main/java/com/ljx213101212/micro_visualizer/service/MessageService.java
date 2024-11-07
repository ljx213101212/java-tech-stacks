package com.ljx213101212.micro_visualizer.service;

import com.ljx213101212.micro_visualizer.model.Message;
import com.ljx213101212.micro_visualizer.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages() {
       return messageRepository.findAll();
    }
}
