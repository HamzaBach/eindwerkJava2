package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
     * @param articleRepository The article repository is autowired to it and used via dependency injection into the ArticleService constructor.
     */
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Method for obtaining all active articles from the data-access layer.
     * @return A list of all active articles retrieved from {@link com.example.eindwerkJava2.repositories.ArticleRepository#findByActiveArticle(int)}.
     */
    public List<Article> getActiveArticles() {
        return this.articleRepository.findByActiveArticle(1);
    }

    /**
     * Method for saving an article.
     * @param article The to be saved article comes in as an {@link com.example.eindwerkJava2.model.Article} object and is saved as such.
     * @param articleImage The article image is saved as a BLOB in the data-access layer.
     *                     Also the unique barcode is generated in this method as a concatenation of: Category abbreviation + Article abbreviation + article id.
     */
    public void saveArticle(Article article, byte[] articleImage){
        if(articleImage.length==0)
        {
            if(articleRepository.existsArticleByArticleId(article.getArticleId())){
                Article currentArticle = articleRepository.getById(article.getArticleId());
                article.setArticleImage(currentArticle.getArticleImage());
            }
        }else{
            article.setArticleImage(articleImage);
        }
        //Generate unique barcode:
        if(articleRepository.existsArticleByArticleId(article.getArticleId())){
            article.setArticleBarcode(article.getCategory().getCategoryAbbreviation()+"-"+article.getArticleAbbreviation()+"-"+article.getArticleId());
        } else{
            article.setArticleBarcode(article.getCategory().getCategoryAbbreviation()+"-"+article.getArticleAbbreviation()+"-"+String.valueOf(articleRepository.getMaxId()+1));
        }
        //Save article
        articleRepository.save(article);
    }

    /**
     * Method to find an article by its id.
     * @param id The id of the article.
     * @return The article object found via the id in the database.
     */
    public Optional<Article> findById(Long id){
        return articleRepository.findById(id);
    }

    /**
     * Method to delete an article from the database.
     * @param article The article object that needs to be deleted.
     */
    public void deleteArticle(Article article){
        article.setActiveArticle(0);
        this.articleRepository.save(article);
    }









}
