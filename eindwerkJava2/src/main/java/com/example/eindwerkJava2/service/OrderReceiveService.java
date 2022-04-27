package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.repositories.OrderReceiveRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderReceiveService {
    private final OrderReceiveRepository orderReceiveRepository;


    public OrderReceiveService(OrderReceiveRepository orderReceiveRepository) {
        this.orderReceiveRepository = orderReceiveRepository;
    }
}
