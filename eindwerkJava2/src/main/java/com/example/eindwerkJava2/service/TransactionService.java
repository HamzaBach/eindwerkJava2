package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.TransactionType;
import com.example.eindwerkJava2.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {


    @Autowired
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionType> getTransactiontypes(){
        return this.transactionRepository.findAll();
    }

    public TransactionType getSaleType(){
        return transactionRepository.findByTransactionTypeName("Opboeken").get();
    }



}
