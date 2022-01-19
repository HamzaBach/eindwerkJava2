package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    boolean existsArticleByArticleId (Long articleId);
    boolean existsArticleBySupplierId(Long supplierId);
    boolean existsArticleByArticleName(String articleName);
    boolean existsArticleByArticleDescription(String articleDescription);
    boolean existsArticleByCategorieId(Long categorieId);
    Article findByArticleId(Long articleId);
}
