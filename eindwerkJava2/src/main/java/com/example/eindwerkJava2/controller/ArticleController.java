package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
//  @RestController
@RequestMapping(path = "articles")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String getArticles(Model model) {
        List<Article> articles = articleService.getArticles();
        model.addAttribute("ArticlesList",articles);
        return "articles";
    }

    @GetMapping(path = "{articleId}")
    public Article getArticle(@PathVariable("articleId") Long articleId) {
        return articleService.getArticle(articleId);
    }

    @PostMapping(path = "newArticle")
    public void addArticle(String articleName, String articleDescription, Category category,
                           Long supplierId, byte[] articleImage) {

        articleService.addArticle(articleName, articleDescription, category, supplierId, articleImage);
    }

}
