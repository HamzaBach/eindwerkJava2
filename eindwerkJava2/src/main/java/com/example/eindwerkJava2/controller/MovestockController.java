package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.service.MutationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class MovestockController {
    private final MutationServiceImpl mutationService;

@Autowired
    public MovestockController(MutationServiceImpl mutationService) {
        this.mutationService = mutationService;
    }



}
