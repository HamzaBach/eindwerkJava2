package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.dto.ArticleDto;
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
    public List<ArticleDto> getAllArticles() {
        return articleService.articleDtos(articleService.getActiveArticles().getEntities());
    }

    @GetMapping("{barcode}")
    public ArticleDto getArticleById(@PathVariable String barcode) {
        return articleService.getDtoOfBarcode(barcode);
    }

}
