package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.repositories.ArticleRepository;
import com.example.eindwerkJava2.wrappers.ArticleSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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


    public ArticleSuccess saveArticle(Article article, byte[] articleImage){
        ArticleSuccess success = new ArticleSuccess();
        if(article.getArticleId()==null
                && articleRepository.existsArticleByArticleName(article.getArticleName())
                && articleRepository.findByArticleName(article.getArticleName()).getActiveArticle()==1){
            success.setIsSuccessfull(false);
            success.setMessage("New article cannot be added because this article name "+article.getArticleName()+" already exists! ");
        }
        // use case if an existing article gets renamed to the name of an already present article name -> block!
        if(article.getArticleId()!=null && articleRepository.findByArticleName(article.getArticleName()).getArticleId()!=article.getArticleId()){
            success.setIsSuccessfull(false);
            success.setMessage("Cannot modify this article because the article name "+article.getArticleName()+" is already used!");
        }
        else{
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
            success.setIsSuccessfull(true);
            success.setMessage("Article "+article.getArticleName()+" is succesfully saved!");
        }
        return success;
    }


    public ArticleSuccess findById(Long id){
        ArticleSuccess success = new ArticleSuccess();
        if(articleRepository.findById(id).isEmpty()){
            success.setIsSuccessfull(false);
            success.extendMessage("Article not found!");
        } else {
            Article article=articleRepository.findById(id).get();
            success.setArticle(article);
            success.setIsSuccessfull(true);;
        }
        return success;
    }


    public ArticleSuccess deleteArticle(Article article){
        ArticleSuccess success = new ArticleSuccess();
        article.setActiveArticle(0);
        this.articleRepository.save(article);
        if(articleRepository.findById(article.getArticleId()).get().getActiveArticle()==0){
            success.setIsSuccessfull(true);
            success.setMessage("Article "+article.getArticleName()+" ("+article.getArticleId()+")"+" was successfully removed.");
        } else{
            success.setIsSuccessfull(false);
            success.setMessage("Article "+article.getArticleName()+" ("+article.getArticleId()+")"+" could not be removed.");
        }
        return success;
    }









}
