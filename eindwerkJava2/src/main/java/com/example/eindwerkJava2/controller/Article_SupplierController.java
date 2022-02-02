package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article_Supplier;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.Article_SupplierService;
import com.example.eindwerkJava2.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Article_SupplierController {

    @Autowired
    private Article_SupplierService article_supplierService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/article_supplier")
    public String viewSuppliers(Model model){
        model.addAttribute("articleSupplierList", article_supplierService.getArticle_Supplier());
        return "article_supplier";
    }



    @GetMapping("/showNewArticleSupplierForm")
    public String showNewSupplierForm(Model model){
        Article_Supplier article_supplier = new Article_Supplier();

        model.addAttribute("article_supplier", new Article_Supplier());
        model.addAttribute("articleList", articleService.getArticles());
        model.addAttribute("supplierList",supplierService.getAllSuppliers() );
        return "form_article_supplier";
    }

    @PostMapping("/saveArticleSupplier")
    public String saveArticleSupplier(@ModelAttribute("article_supplier") Article_Supplier article_supplier){
        this.article_supplierService.saveArticleSupplier(article_supplier);
        return "redirect:/article_supplier";
    }
}
