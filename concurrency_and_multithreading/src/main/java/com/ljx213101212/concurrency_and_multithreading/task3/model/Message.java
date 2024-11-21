package com.ljx213101212.concurrency_and_multithreading.task3.model;

public class Message {
    private final String topic;
    private final String payload;

    public Message(String topic, String payload) {
        this.topic = topic;
        this.payload = payload;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "Topic: " + topic + ", Payload: " + payload;
    }
}
