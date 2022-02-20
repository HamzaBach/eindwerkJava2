package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.ArticleSupplierService;
import com.example.eindwerkJava2.service.CategoryService;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
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
    private final ArticleSupplierService articleSupplierService;


    public ArticleController(ArticleService articleService,
                             CategoryService categoryService,
                             ArticleSupplierService articleSupplier) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.articleSupplierService =articleSupplier;
    }

    @GetMapping("/landing_page")
    public String getLandingPage(){
        return "landing_page";
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        model.addAttribute("article",new Article());
//        model.addAttribute("categoriesList", categoryService.getCategories());
//        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
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
//        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
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
    public String showEditarticleForm(@PathVariable("articleId") Long articleId, Model model) {
        Article article = articleService.findById(articleId).get();
        model.addAttribute("article", article);
        model.addAttribute("categoriesList", categoryService.getCategories());
        model.addAttribute("articleSuppliersList", articleSupplierService.getAllSuppliersPerArticle(article));
        return "form_article";
    }

//    @GetMapping(value="/article/barcode/{articleId}", produces = MediaType.IMAGE_PNG_VALUE)
//    @ResponseBody
//    void showBarcodeImage(@PathVariable("articleId") Long articleId, HttpServletResponse response, Optional<Article> article)
//            throws IOException, BarcodeException, OutputException {
//        byte[] barcode = articleService.getBarcodeImage(articleId);
//        if(article.get().getArticleBarcode()!=null){
//            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//            response.getOutputStream().write(barcode);
//            response.getOutputStream().close();
//        }
//    }


    @GetMapping(value="/article/barcode/{articleId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barbecueEAN13Barcode(@PathVariable("articleId") Long articleId)
            throws Exception {
        Article article = articleService.findById(articleId).get();
        Barcode barcode = BarcodeFactory.createCode128(article.getArticleBarcode());
//      Method to alter the font: barcode.setFont(BARCODE_TEXT_FONT);
        return ResponseEntity.ok(BarcodeImageHandler.getImage(barcode));
    }
    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

}




