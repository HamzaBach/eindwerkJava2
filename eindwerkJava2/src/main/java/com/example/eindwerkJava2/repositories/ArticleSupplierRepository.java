package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ArticleSupplierRepository extends JpaRepository<ArticleSupplier, Long> {
    @Query(value = "select * from article_supplier where supplier in (select supplier_Id from supplier where active =1) and active=1", nativeQuery = true)
    List<ArticleSupplier> getAllArticleSuppliers();

    List<ArticleSupplier>findByArticle(Article article);
//    boolean existsArticleSupplierByArticle(Article article);
    List<ArticleSupplier> findBySupplier(Long supplierId);

    @Query(value = "select * from article_supplier where supplier =?1 and active=1", nativeQuery = true)
    List<ArticleSupplier> getActiveArticlesFromSpecificSupplier(Long supplierId);




}
