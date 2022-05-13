package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.dto.ArticleDto;
import com.example.eindwerkJava2.repositories.ArticleRepository;
import com.example.eindwerkJava2.wrappers.ArticleSuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The service layer which hosts the business logic for articles.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Service
public class ArticleService {
    @Autowired
    private final ArticleRepository articleRepository;

    /**
     * The ArticleService constructor creates the service layer for articles.
     *
     * @param articleRepository The article repository is autowired to it and used via dependency injection into the ArticleService constructor.
     */
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Method for obtaining all active articles from the data-access layer.
     *
     * @return A list of all active articles retrieved from {@link com.example.eindwerkJava2.repositories.ArticleRepository#findByActiveArticle(int)}.
     */
    public ArticleSuccess getActiveArticles() {
        ArticleSuccess retrievedArticles = new ArticleSuccess();
        List<Article> activeArticles = this.articleRepository.findByActiveArticle(1);
        if (activeArticles.size() > 0) {
            retrievedArticles.setArticles(activeArticles);
            retrievedArticles.setIsSuccessfull(true);
        } else {
            retrievedArticles.setIsSuccessfull(false);
            retrievedArticles.setMessage("No articles found within the database.");
        }
        return retrievedArticles;
    }

    /**
     * Method to save a new or edited article in the database.
     *
     * @param article      The to be saved article.
     * @param articleImage The article image.
     * @return The successObject (wrapper class around article {@link com.example.eindwerkJava2.wrappers.ArticleSuccess}) indicating whether the save action was successful or not.
     */

    public SuccessObject saveArticle(Article article, byte[] articleImage) {
        SuccessObject isSaveSuccessful = new ArticleSuccess();
        Boolean existsArticleByName = articleRepository.existsArticleByArticleName(article.getArticleName());
        if (existsArticleByName) {
            Article articleWithSameName = articleRepository.findByArticleName(article.getArticleName()).get();
            // use case if a new article gets named to the name of an already present article name -> block!
            if (article.getArticleId() == null
                    && articleWithSameName.getActiveArticle() == 1) {
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("New article cannot be added because this article name " + article.getArticleName() + " already exists!");
                return isSaveSuccessful;
            }
            // use case if an existing article gets renamed to the name of an already present article name -> block!
            if (article.getArticleId() != null
                    && articleWithSameName.getArticleId() != article.getArticleId()
                    && articleWithSameName.getActiveArticle() == 1) {
                isSaveSuccessful.setIsSuccessfull(false);
                isSaveSuccessful.setMessage("Cannot modify this article because the article name " + article.getArticleName() + " already exists!");
                return isSaveSuccessful;
            }
        }
        articleImageHandler(article, articleImage);
        //Generate unique barcode:
        articleBarcodeHandler(article);
        //Save article
        articleRepository.save(article);
        isSaveSuccessful.setIsSuccessfull(true);
        isSaveSuccessful.setMessage("Article " + article.getArticleName() + " is successfully saved!");
        return isSaveSuccessful;

    }

    private void articleBarcodeHandler(Article article) {
        if (articleRepository.existsArticleByArticleId(article.getArticleId())) {
            article.setArticleBarcode(article.getCategory().getCategoryAbbreviation() + "-" + article.getArticleAbbreviation() + "-" + article.getArticleId());
        } else {
            article.setArticleBarcode(article.getCategory().getCategoryAbbreviation() + "-" + article.getArticleAbbreviation() + "-" + String.valueOf(articleRepository.getMaxId() + 1));
        }
    }

    private void articleImageHandler(Article article, byte[] articleImage) {
        if (articleImage.length == 0) {
            if (articleRepository.existsArticleByArticleId(article.getArticleId())) {
                Article currentArticle = articleRepository.getById(article.getArticleId());
                article.setArticleImage(currentArticle.getArticleImage());
            }
        } else {
            article.setArticleImage(articleImage);
        }
    }

    /**
     * Method to retrieve an article from the database based on its articleId.
     *
     * @param id The id of the to be retrieved article from the database.
     * @return The successObject (wrapper around article {@link com.example.eindwerkJava2.wrappers.ArticleSuccess}) to indicate whether the find action was successful or not.
     */
    public ArticleSuccess findById(Long id) {
        ArticleSuccess success = new ArticleSuccess();
        Boolean existsArticle = articleRepository.existsArticleByArticleId(id);
        if(existsArticle){
            Article article = articleRepository.findById(id).get();
            success.setArticle(article);
            success.setIsSuccessfull(true);
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("Article not found!");
        }

        return success;
    }
    /**
     * Method to retrieve an article from the database based on its barcode.
     *
     * @param barcode The barcode of the to be retrieved article from the database.
     * @return The successObject (wrapper around article {@link com.example.eindwerkJava2.wrappers.ArticleSuccess}) to indicate whether the find action was successful or not.
     */
    public ArticleSuccess findByBarcode(String barcode) {
        ArticleSuccess success = new ArticleSuccess();
        if (articleRepository.findByArticleBarcode(barcode).isEmpty()) {
            success.setIsSuccessfull(false);
            success.setMessage("Article not found!");
        } else {
            Article article = articleRepository.findByArticleBarcode(barcode).get();
            success.setArticle(article);
            success.setIsSuccessfull(true);
        }
        return success;
    }

    /**
     * Method to delete an article from the database.
     *
     * @param article The to be deleted article.
     * @return The successObject (wrapper around article {@link com.example.eindwerkJava2.wrappers.ArticleSuccess}) to indicate whether the delete action was successful or not.
     */

    public SuccessObject deleteArticle(Article article) {
        SuccessObject success = new ArticleSuccess();
        article.setActiveArticle(0);
        this.articleRepository.save(article);
        if (articleRepository.findById(article.getArticleId()).get().getActiveArticle() == 0) {
            success.setIsSuccessfull(true);
            success.setMessage("Article " + article.getArticleName() + " (ID: " + article.getArticleId() + ")" + " was successfully removed.");
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("Article " + article.getArticleName() + " (ID: " + article.getArticleId() + ")" + " could not be removed.");
        }
        return success;
    }


    public ArticleDto getDtoOfBarcode(String barcode){
        Article article = articleRepository.findByArticleBarcode(barcode).get();
        return toArticleDto(article);
    }

    public ArticleDto toArticleDto(Article article){
        return new ArticleDto(
                article.getArticleName(),
                article.getCategory().getCategoryName(),
                article.getArticleSupplier().getSalesPrice()
        );

    }
    public List<ArticleDto> articleDtos(List<Article> articles){
        return articles.stream()
                .map(this::toArticleDto)
                .collect(Collectors.toList());
    }

}
