package com.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Inventory {
    
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "${topic.inventory}", groupId = "Inventory_group")
    public void consume(String message) {
        System.out.println("Received Order: " + message);
        messages.add(message);
    }

    public List<String> getMessage() {
        return messages;
    }
}
