package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.service.ArticleService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/articles")
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getActiveArticles().getArticles();
    }

    @GetMapping("{barcode}")
    public Article getArticleById(@PathVariable String barcode) {
        return articleService.findByBarcode(barcode).getArticle();
    }

}
