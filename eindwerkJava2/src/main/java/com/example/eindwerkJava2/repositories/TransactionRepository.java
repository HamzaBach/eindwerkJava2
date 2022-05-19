package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionType, Long> {
    boolean existsByTransactionTypeName(String name);
    Optional<TransactionType> findByTransactionTypeName(String name);
}
