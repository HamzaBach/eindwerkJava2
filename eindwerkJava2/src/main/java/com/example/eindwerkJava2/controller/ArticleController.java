package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getArticles() {
        return articleService.getArticles();
    }

    @GetMapping(path = "{articleId}")
    public Article getArticle(@PathVariable("articleId") Long articleId) {
        return articleService.getArticle(articleId);
    }

    @PostMapping(path = "newArticle")
    public void addArticle(String articleName, String articleDescription, Long categoryId,
                           Long supplierId, String thumbNail) {
        articleService.addArticle(articleName, articleDescription, categoryId, supplierId, thumbNail);
    }

}
