package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    boolean existsArticleByArticleId (Long articleId);
    boolean existsArticleBySupplier(Supplier supplier);
    boolean existsArticleByArticleName(String articleName);
    boolean existsArticleByArticleDescription(String articleDescription);
    boolean existsArticleByCategory(Category category);
    Article findByArticleId(Long articleId);
    List<Article> findByActiveArticle(int activeArticle);
    boolean updateArticle (Article article);
}
