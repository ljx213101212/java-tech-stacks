package com.ljx213101212.micro_recipient.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageStorageService {

    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        List<String> currentMessages = new ArrayList<>(messages);
        messages.clear(); // Clear messages after returning
        return currentMessages;
    }
}
