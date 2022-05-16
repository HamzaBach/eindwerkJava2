
package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;

import java.util.List;
import java.util.Optional;

public interface MutationService {


     List<Mutation> getMutations();

     SuccessEvaluator<Mutation> addStock(Mutation mutation);
     String removeStock(Mutation mutation);
     void moveStock(Mutation mutation);

     Optional<Mutation> findById(Long id);
     void deleteMutation(Mutation mutation);
     Double getArticleAmount(Mutation mutation);
}
