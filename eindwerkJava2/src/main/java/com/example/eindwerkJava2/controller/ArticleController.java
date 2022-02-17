package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.CategoryService;
import com.example.eindwerkJava2.service.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

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
        List<Article> articles = articleService.getActiveArticles();
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
        List<Article> articles = articleService.getActiveArticles();
        model.addAttribute("articlesList",articles);
        return "form_article";
    }

    @PostMapping("/saveArticle")
    public String saveArticle(@ModelAttribute("article") Article article,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        byte[] addedImage = multipartFile.getBytes();
        this.articleService.saveArticle(article,addedImage);
        return "redirect:/articles";
    }

    @GetMapping("/article/image/{articleId}")
    @ResponseBody
    void showImage(@PathVariable("articleId") Long articleId, HttpServletResponse response, Optional<Article> article)
            throws IOException {
        article = articleService.findById(articleId);
        if(article.get().getArticleImage()!=null){
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(article.get().getArticleImage());
            response.getOutputStream().close();
        }
    }

    @GetMapping("editArticle/{articleId}")
    public String showEditarticleForm(@PathVariable("articleId") Long articleId, Model model) throws UnsupportedEncodingException {
        Article article = articleService.findById(articleId).get();
        model.addAttribute("article", article);
        model.addAttribute("categoriesList", categoryService.getCategories());
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        return "form_article";
    }
}
