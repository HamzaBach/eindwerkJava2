package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.ArticleSupplierService;
import com.example.eindwerkJava2.service.SupplierService;
import com.example.eindwerkJava2.wrappers.ArticleSuccess;
import com.example.eindwerkJava2.wrappers.ArticleSupplierSuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import com.example.eindwerkJava2.wrappers.SupplierSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        ArticleSupplierSuccess retrievedArticlesSuppliers = articleSupplierService.getAllArticleSuppliers();
        if(retrievedArticlesSuppliers.getIsSuccessfull()){
            model.addAttribute("articleSupplierList", retrievedArticlesSuppliers.getArticlesSuppliers());
        } else{
            model.addAttribute("error", retrievedArticlesSuppliers.getMessage());
        }
        return "article_supplier";
    }

    @GetMapping("/new/articleSupplier")
    public String showNewArticleSupplierForm(Model model){
        SupplierSuccess supplierSuccess = supplierService.getAllSuppliers();
        ArticleSupplier articleSupplier = new ArticleSupplier();
        ArticleSuccess retrievedArticles = articleService.getActiveArticles();
        if(retrievedArticles.getIsSuccessfull()){
            model.addAttribute("articleSupplier", articleSupplier);
            model.addAttribute("articleList", retrievedArticles.getArticles());
            model.addAttribute("supplierList", supplierSuccess.getSuppliers());
        } else {
            model.addAttribute("error", retrievedArticles.getMessage());
        }

        return "/forms/form_article_supplier";
    }

    @PostMapping("/saveArticleSupplier")
    public String saveArticleSupplier(@ModelAttribute("articleSupplier") ArticleSupplier articleSupplier, Model model, RedirectAttributes redirAttrs){
        SuccessObject success = this.articleSupplierService.saveArticleSupplier(articleSupplier);
        if (success.getIsSuccessfull()) {
            redirAttrs.addFlashAttribute("success", success.getMessage());
            return "redirect:/article_supplier";
        } else {
            model.addAttribute("error", success.getMessage());
            // This below is needed to be able to fill in the combo-boxes via model attributes of the other methods.
            if(articleSupplier.getArticleSupplierId()!=null){
                return editArticleSupplier(articleSupplier.getArticleSupplierId(),model);
            }else{
                return showNewArticleSupplierForm(model);
            }
        }
    }

    @GetMapping("edit/articleSupplier/{articleSupplierId}")
    public String editArticleSupplier(@PathVariable("articleSupplierId") Long articleSupplierId, Model model){
        SupplierSuccess supplierSuccess = supplierService.getAllSuppliers();
        ArticleSupplierSuccess success = articleSupplierService.findById(articleSupplierId);
        if(success.getIsSuccessfull()){
            ArticleSupplier articleSupplier = success.getArticleSupplier();
            model.addAttribute("articleSupplier", articleSupplier);
            model.addAttribute("articleList", articleService.getActiveArticles().getArticles());
            model.addAttribute("supplierList", supplierSuccess.getSuppliers());
        } else{
            model.addAttribute("error",success.getMessage());
        }
        return "/forms/form_article_supplier";
    }
    @GetMapping("delete/articleSupplier/{articleSupplierId}")
    public String deleteArticleSupplier(@PathVariable("articleSupplierId") Long articleSupplierId, RedirectAttributes redirAttrs){
        ArticleSupplierSuccess findArticleSupplier = articleSupplierService.findById(articleSupplierId);
        if(findArticleSupplier.getIsSuccessfull()){
            SuccessObject toBeDeletedArticleSupplier = articleSupplierService.deleteArticleSupplier(findArticleSupplier.getArticleSupplier());
            if(toBeDeletedArticleSupplier.getIsSuccessfull()){
                redirAttrs.addFlashAttribute("success",toBeDeletedArticleSupplier.getMessage());
            }else{
                redirAttrs.addFlashAttribute("error",toBeDeletedArticleSupplier.getMessage());
            }
        } else{
            redirAttrs.addFlashAttribute("error",findArticleSupplier.getMessage());
        }
        return "redirect:/article_supplier";
    }
}
