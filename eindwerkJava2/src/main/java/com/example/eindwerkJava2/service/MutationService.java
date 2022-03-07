package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.model.TransactionType;
import com.example.eindwerkJava2.repositories.MutationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MutationService {
    private final MutationRepository mutationRepository;

    @Autowired
    public MutationService(MutationRepository mutationRepository) {
        this.mutationRepository = mutationRepository;
    }

    public List<Mutation> getMutations(){return mutationRepository.findAll();}

    public void addMutation(Mutation mutation){mutationRepository.save(mutation);}


}
