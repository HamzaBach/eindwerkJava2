package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.CategoryService;
import com.example.eindwerkJava2.service.SupplierService;
import com.example.eindwerkJava2.service.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping/*(path = "articles")*/
public class ArticleController {
    @Autowired
    private final ArticleService articleService;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final SupplierServiceImpl supplierService;


    public ArticleController(ArticleService articleService,
                             CategoryService categoryService,
                             SupplierServiceImpl supplierService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.supplierService=supplierService;
    }

    @GetMapping("/landing_page")
    public String getLandingPage(){
        return "landing_page";
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        model.addAttribute("article",new Article());
        model.addAttribute("categoriesList", categoryService.getCategories());
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articlesList",articles);
        return "articles";
    }

    @GetMapping("deleteArticle/{articleId}")
    public String deleteArticle(@PathVariable("articleId") Long articleId){
        Article article = articleService.findById(articleId).get();
        this.articleService.deleteArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/showNewArticleForm")
    public String showNewArticleForm(Model model) {
        model.addAttribute("article",new Article());
        model.addAttribute("categoriesList", categoryService.getCategories());
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        List<Article> articles = articleService.getArticles();
        model.addAttribute("articlesList",articles);
        return "form_article";
    }

    @PostMapping("/saveArticle")
    public String saveArticle(@ModelAttribute("article") Article article){
        this.articleService.saveArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("editArticle/{articleId}")
    public String showEditarticleForm(@PathVariable("articleId") Long articleId, Model model){
        Article article = articleService.findById(articleId).get();
        model.addAttribute("article", article);
        model.addAttribute("categoriesList", categoryService.getCategories());
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        return "form_article";
    }



//    @GetMapping(path = "{articleId}")
//    public Article getArticle(@PathVariable("articleId") Long articleId) {
//        return articleService.getArticle(articleId);
//    }



}
