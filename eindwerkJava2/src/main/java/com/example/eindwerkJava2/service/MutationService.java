
package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;

import java.util.List;
import java.util.Optional;

public interface MutationService {


     SuccessEvaluator<Mutation> getMutations();

     SuccessEvaluator<Mutation> addStock(Mutation mutation);
     SuccessEvaluator<Mutation> removeStock(Mutation mutation);
     SuccessEvaluator<Mutation> moveStock(Mutation mutation , long targetLocationId);

     Optional<Mutation> findById(Long id);
     void deleteMutation(Mutation mutation);
     Double getArticleAmount(Mutation mutation);
     List<Mutation> findByArticle(Article article);
}
