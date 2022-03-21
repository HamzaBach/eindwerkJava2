package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    boolean existsArticleByArticleId (Long articleId);
    boolean existsArticleByArticleName(String articleName);
    boolean existsArticleByArticleDescription(String articleDescription);
    boolean existsArticleByCategory(Category category);
    Article findByArticleId(Long articleId);
    List<Article> findByActiveArticle(int activeArticle);
    @Query(value = "select MAX(article_id) AS Max_Id from article",nativeQuery = true)
    Long getMaxId();

}
