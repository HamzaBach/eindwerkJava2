package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MutationRepository extends JpaRepository<Mutation,Long> {

    List<Mutation> findByArticle(Article article);

    @Query(value = "SELECT * FROM mutation WHERE local_date_time > ?1 AND transaction_type_id = ?2", nativeQuery = true)
    List<Mutation> getMutationsWithStartingDateAndTransactionType(LocalDateTime startDate, TransactionType transactionType);


}
