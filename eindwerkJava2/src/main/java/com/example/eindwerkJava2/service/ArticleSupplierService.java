package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.ArticleSupplierRepository;
import com.example.eindwerkJava2.wrappers.ArticleSupplierSuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleSupplierService {
    @Autowired
    private ArticleSupplierRepository articleSupplierRepository;

    public ArticleSupplierSuccess getAllArticleSuppliers() {
        ArticleSupplierSuccess retrievedSuppliersArticles = new ArticleSupplierSuccess();
        List<ArticleSupplier> activeArticlesSuppliers = this.articleSupplierRepository.getAllArticleSuppliers();
        if (activeArticlesSuppliers.size() > 0) {
            retrievedSuppliersArticles.setIsSuccessfull(true);
            retrievedSuppliersArticles.setArticlesSuppliers(activeArticlesSuppliers);
        } else {
            retrievedSuppliersArticles.setIsSuccessfull(false);
            retrievedSuppliersArticles.setMessage("No articles from suppliers found within the database.");
        }
        return retrievedSuppliersArticles;
    }

    public SuccessObject saveArticleSupplier(ArticleSupplier articleSupplier) {
        SuccessObject isSaveSuccessful = new ArticleSupplierSuccess();
        List<ArticleSupplier> articlesFromSupplier = articleSupplierRepository.getActiveArticlesFromSpecificSupplier(articleSupplier.getSupplier().getSupplierId());
        duplicateArticlesFromSupplierHandler(articleSupplier, isSaveSuccessful, articlesFromSupplier);
        if(isSaveSuccessful.getIsSuccessfull()){
            this.articleSupplierRepository.save(articleSupplier);
        }
        return isSaveSuccessful;
    }

    private void duplicateArticlesFromSupplierHandler(ArticleSupplier articleSupplier, SuccessObject isSaveSuccessful, List<ArticleSupplier> articlesFromSupplier) {
        //use case when a new article (id==null) of a supplier is added while the same one is already present in the repository -> block!
        //use case when an existing article (id!=null) gets modified to refer to an article which is already provided by the same supplier -> block!
        int duplicatesCounter =0;
        Long duplicateArticleSupplierId = -1L;
        for (ArticleSupplier articleSupplier1 : articlesFromSupplier) {
            if (articleSupplier.getArticle().getArticleName().equals(articleSupplier1.getArticle().getArticleName()) && articleSupplier.getArticleSupplierId() == null ||
                    articleSupplier.getArticle().getArticleName().equals(articleSupplier1.getArticle().getArticleName()) && articleSupplier.getArticleSupplierId() != articleSupplier1.getArticleSupplierId()) {
                duplicatesCounter+=1;
                duplicateArticleSupplierId=articleSupplier1.getArticleSupplierId();
            }
        }
        if(duplicatesCounter>0){
            isSaveSuccessful.setIsSuccessfull(false);
            isSaveSuccessful.setMessage("Article " + articleSupplier.getArticle().getArticleName() + " from supplier "
                    + articleSupplier.getSupplier().getSupplierName() + " cannot be saved because this same article is already provided by supplier "
                    + articleSupplier.getSupplier().getSupplierName() + " with articleSupplierId: " + duplicateArticleSupplierId);
        } else{
            isSaveSuccessful.setIsSuccessfull(true);
            isSaveSuccessful.setMessage("Article " + articleSupplier.getArticle().getArticleName() + " from supplier "
                    + articleSupplier.getSupplier().getSupplierName() + " is successfully saved.");
        }
    }

    public Optional<ArticleSupplier> findById(long id) {
        return articleSupplierRepository.findById(id);
    }

    public void deleteArticleSupplier(ArticleSupplier articleSupplier) {
        articleSupplier.setActiveArticleSupplier(0);
        this.articleSupplierRepository.save(articleSupplier);
    }

    public List<ArticleSupplier> getAllSuppliersPerArticle(Article article) {
        List<ArticleSupplier> suppliersForArticle = articleSupplierRepository.findByArticle(article);
        Iterator<ArticleSupplier> i = suppliersForArticle.iterator();
        while (i.hasNext()) {
            ArticleSupplier articleSupplier = i.next();
            //Removal of suppliers which are inactive
            if (articleSupplier.getActiveArticleSupplier() == 0) {
                i.remove();
            }
        }
        return suppliersForArticle;
    }

    public List<Article> getArticlesFromSupplier(Supplier supplier) {
        List<Article> articleList = new ArrayList<>();
        List<ArticleSupplier> articleSuppliers = getAllArticleSuppliers().getArticlesSuppliers();
        for (ArticleSupplier articleSupplier : articleSuppliers) {
            if (articleSupplier.getSupplier().equals(supplier)) {
                articleList.add(articleSupplier.getArticle());
            }
        }
        return articleList;
    }
}
