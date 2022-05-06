package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.ArticleSupplierService;
import com.example.eindwerkJava2.service.CategoryService;
import com.example.eindwerkJava2.wrappers.ArticleSuccess;
import com.example.eindwerkJava2.wrappers.CategorySuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * The controller layer for connecting the front-end with the back-end for articles.
 *
 * @author Hamza
 * @version 1.0
 */
@Controller
@RequestMapping
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
        this.articleSupplierService = articleSupplier;
    }

    /**
     * Endpoint (GET) that returns the homepage.
     *
     * @return The home page is returned.
     */
    @GetMapping("/home")
    public String getHomePage() {
        return "landing_page";
    }

    /**
     * Endpoint (GET) that returns a list of all articles (active).
     *
     * @param model List of articles is added as an attribute via a model for the front-end to use.
     * @return An overview page of all articles is returned.
     */
    @GetMapping("/articles")
    public String getArticles(Model model) {
        ArticleSuccess retrievedArticles = articleService.getActiveArticles();
        if (!retrievedArticles.getIsSuccessfull()) {
            model.addAttribute("error", retrievedArticles.getMessage());
        } else {
            model.addAttribute("article", new Article());
            List<Article> articles = retrievedArticles.getArticles();
            model.addAttribute("articlesList", articles);
        }
        return "articles";
    }


    @GetMapping("delete/article/{articleId}")
    public String deleteArticle(@PathVariable("articleId") Long articleId, RedirectAttributes redirAttrs) {
        ArticleSuccess findArticle = articleService.findById(articleId);
        if(findArticle.getIsSuccessfull()){
            SuccessObject toBeDeletedArticle = this.articleService.deleteArticle(findArticle.getArticle());
            if (toBeDeletedArticle.getIsSuccessfull()) {
                redirAttrs.addFlashAttribute("success", toBeDeletedArticle.getMessage());
            } else {
                redirAttrs.addFlashAttribute("error", toBeDeletedArticle.getMessage());
            }
        } else{
            redirAttrs.addFlashAttribute("error",findArticle.getMessage());
        }
        return "redirect:/articles";
    }

    /**
     * Endpoint (GET) to fill in the attributes of a new article.
     *
     * @param model Attributes are added to the model for the front-end to use.
     *              Attributes include: new Article object, categoriesList and articlesList.
     * @return A form is returned to fill in all attributes of the new article.
     */
    @GetMapping("/new/article")
    public String showNewArticleForm(Model model) {
        CategorySuccess categorySuccess = categoryService.getCategories();
        model.addAttribute("article", new Article());
        if (categorySuccess.getIsSuccessfull()) {
            model.addAttribute("categoriesList", categorySuccess.getCategories());
        } else {
            model.addAttribute("error", categorySuccess.getMessage());
        }
        return "/forms/form_article";
    }

    /**
     * Endpoint (POST) to save a new article.
     *
     * @param article       The newly defined article is stored in an {@link com.example.eindwerkJava2.model.Article} object.
     * @param multipartFile The uploaded article picture is retrieved via a multipartfile from the front-end before being stored as a blob in the back-end.
     * @param redirAttrs    The redirect attribute is used for the error/success message handling.
     * @return An overview page of all articles is returned via the endpoint {@link com.example.eindwerkJava2.controller.ArticleController#getArticles(Model)}.
     * @throws IOException
     */
    @PostMapping("/saveArticle")
    public String saveArticle(@ModelAttribute("article") Article article,
                              @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirAttrs, Model model) throws IOException {
        byte[] addedImage = multipartFile.getBytes();
        SuccessObject success = this.articleService.saveArticle(article, addedImage);
        if (success.getIsSuccessfull()) {
            redirAttrs.addFlashAttribute("success", success.getMessage());
            return "redirect:/articles";
        } else {
            model.addAttribute("error", success.getMessage());
            return "/forms/form_article";
        }
    }

    /**
     * Endpoint (GET) to display the article image in the front-end.
     *
     * @param articleId The article id is used to retrieve the picture from the back-end.
     * @param response  The response is used for the front-end to set-up the content type and output stream.
     * @throws IOException In case something goes wrong with the data exchange an IOException could be triggered.
     */
    @GetMapping("/article/image/{articleId}")
    @ResponseBody
    void showImage(@PathVariable("articleId") Long articleId, HttpServletResponse response)
            throws IOException {
        ArticleSuccess success = articleService.findById(articleId);
        if (success.getArticle().getArticleImage() != null && success.getIsSuccessfull()) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(success.getArticle().getArticleImage());
            response.getOutputStream().close();
        }
    }

    /**
     * Endpoint (GET) to edit a particular article.
     *
     * @param articleId The article id is used to retrieve the to be edited article from the back-end.
     * @param model     Attributes are added to the model for the front-end to use.
     *                  Attributes include: the to be edited Article object, categoriesList and articlesSuppliersList.
     * @return A form is returned with all the attributes of the to be edited article.
     */
    @GetMapping("edit/article/{articleId}")
    public String showEditArticleForm(@PathVariable("articleId") Long articleId, Model model) {
        ArticleSuccess success = articleService.findById(articleId);
        if(success.getIsSuccessfull()){
            Article article = success.getArticle();
            model.addAttribute("article", article);
            model.addAttribute("categoriesList", categoryService.getCategories());
            model.addAttribute("articleSuppliersList", articleSupplierService.getAllSuppliersPerArticle(article));
        } else {
            model.addAttribute("error",success.getMessage());
        }
        return "/forms/form_article";
    }


}




