
package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.Exceptions.NegativeInventoryException;
import com.example.eindwerkJava2.model.Mutation;

import java.util.List;
import java.util.Optional;

public interface MutationService {



    public List<Mutation> getMutations();
    public void addMutation(Mutation mutation)
            throws NegativeInventoryException;


    public Optional<Mutation> findById(Long id);
    public void deleteMutation(Mutation mutation);
    public Double updateArticleAmount(Mutation mutation);
}
