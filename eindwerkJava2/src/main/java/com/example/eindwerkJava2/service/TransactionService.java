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

    public TransactionType getOutboundTransactionType(){
        return transactionRepository.findByTransactionTypeName("Afboeken").get();
    }

    public TransactionType getInboundTransactionType(){
        return transactionRepository.findByTransactionTypeName("Opboeken").get();
    }

    public TransactionType getInternalRemovalTransactionType(){
        return transactionRepository.findByTransactionTypeName("Intern afboeken").get();
    }

    public TransactionType getInternalAdditionTransactionType(){
        return transactionRepository.findByTransactionTypeName("Intern opboeken").get();
    }

    public TransactionType getCorrectionAdditionTransactionType(){
        return transactionRepository.findByTransactionTypeName("Correctie opboeken").get();
    }

    public TransactionType getCorrectionRemovalTransactionType(){
        return transactionRepository.findByTransactionTypeName("Correctie afboeken").get();
    }



}
