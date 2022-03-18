package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private final ArticleRepository articleRepository;


    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getActiveArticles() {
        return this.articleRepository.findByActiveArticle(1);
    }

    public Article getArticle(Long articleId){
        if(articleRepository.existsArticleByArticleId(articleId)){
            return articleRepository.findByArticleId(articleId);
        } else throw new IllegalStateException("Article with id " + articleId + " does not exist!");
    }

    public Boolean addArticle(String articleName, String articleDescription, Category category,
                              ArticleSupplier articleSupplier, byte[] articleImage, int activeArticle, String articleAbbreviation) {

        if (articleRepository.existsArticleByArticleName(articleName)
                && articleRepository.existsArticleByArticleDescription(articleDescription)
                && articleRepository.existsArticleByCategory(category)) {
            return false;
        } else{
            Article newArticle = new Article(articleName,articleDescription,category, articleImage, activeArticle, articleAbbreviation);
            articleRepository.save(newArticle);
            return true;
        }
    }
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
        article.setArticleBarcode(article.getCategory().getCategoryAbbreviation()+"-"+article.getArticleAbbreviation()+"-"+article.getArticleId());
        //Save article
        articleRepository.save(article);
    }

    public Optional<Article> findById(Long id){
        return articleRepository.findById(id);
    }

    public void deleteArticle(Article article){
        article.setActiveArticle(0);
        this.articleRepository.save(article);
    }









}
