package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.ArticleSupplierService;
import com.example.eindwerkJava2.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleSupplierController {
    @Autowired
    private ArticleSupplierService articleSupplierService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/article_supplier")
    public String viewSuppliers(Model model){
        model.addAttribute("articleSupplierList", articleSupplierService.getAllArticleSuppliers());
        return "article_supplier";
    }

    @GetMapping("/showNewArticleSupplierForm")
    public String showNewArticleSupplierForm(Model model){
        ArticleSupplier articleSupplier = new ArticleSupplier();
        model.addAttribute("articleSupplier", new ArticleSupplier());
        model.addAttribute("articleList", articleService.getActiveArticles());
        model.addAttribute("supplierList", supplierService.getAllSuppliers());
        return "form_article_supplier";
    }

    @PostMapping("/saveArticleSupplier")
    public String saveArticleSupplier(@ModelAttribute("articleSupplier") ArticleSupplier articleSupplier){
        this.articleSupplierService.saveArticleSupplier(articleSupplier);
        return "redirect:/article_supplier";
    }

    @GetMapping("editArticleSupplier/{articleSupplierId}")
    public String editArticleSupplier(@PathVariable("articleSupplierId") Long articleSupplierId, Model model){
        ArticleSupplier articleSupplier = articleSupplierService.findById(articleSupplierId).get();
        model.addAttribute("articleSupplier", articleSupplier);
        model.addAttribute("articleList", articleService.getActiveArticles());
        model.addAttribute("supplierList", supplierService.getAllSuppliers());
        return "form_article_supplier";
    }
    @GetMapping("deleteArticleSupplier/{articleSupplierId}")
    public String deleteArticleSupplier(@PathVariable("articleSupplierId") Long articleSupplierId, Model model){
        ArticleSupplier articleSupplier = articleSupplierService.findById(articleSupplierId).get();
        this.articleSupplierService.deleteArticleSupplier(articleSupplier);
        return "redirect:/article_supplier";
    }
}
