package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticles() {
        return this.articleRepository.findAll();
    }

    public Article getArticle(Long articleId){
        if(articleRepository.existsArticleByArticleId(articleId)){
            return articleRepository.findByArticleId(articleId);
        } else throw new IllegalStateException("Article with id " + articleId + " does not exist!");
    }

    public Boolean addArticle(String articleName, String articleDescription, Long categoryId,
                              Long supplierId, byte[] articleImage) {

        if (articleRepository.existsArticleBySupplierId(supplierId)
                && articleRepository.existsArticleByArticleName(articleName)
                && articleRepository.existsArticleByArticleDescription(articleDescription)
                && articleRepository.existsArticleByCategoryId(categoryId)) {
            return false;
        } else{
            Article newArticle = new Article(articleName,articleDescription,categoryId,supplierId, articleImage);
            articleRepository.save(newArticle);
            return true;
        }
    }


}
