package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.ArticleSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ArticleSupplierRepository extends JpaRepository<ArticleSupplier, Long> {

}
