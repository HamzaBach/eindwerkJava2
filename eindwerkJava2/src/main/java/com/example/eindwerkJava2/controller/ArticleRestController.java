package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ArticleRestController {


    @Autowired
    private final ArticleService articleService;
    public ArticleRestController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping("/testje")
    public List<Article> testArticles(){
        List<Article> articles = articleService.getActiveArticles();
        return articles;
    }

    @GetMapping("/testing/{articleId}")
    @ResponseBody
        public Article testArticleById(@PathVariable("articleId") Long articleId){
        Article article = articleService.findById(articleId).get();

        return article;}


}
