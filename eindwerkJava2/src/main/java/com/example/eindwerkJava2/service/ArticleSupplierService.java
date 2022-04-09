package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.ArticleSupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleSupplierService {
    @Autowired
    private ArticleSupplierRepository articleSupplierRepository;

    public List<ArticleSupplier> getAllArticleSuppliers() {
        return articleSupplierRepository.getAllArticleSuppliers();
    }

    public void saveArticleSupplier(ArticleSupplier articleSupplier) {
        this.articleSupplierRepository.save(articleSupplier);
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
        List<ArticleSupplier> articleSuppliers = getAllArticleSuppliers();
        for (ArticleSupplier articleSupplier : articleSuppliers) {
            if (articleSupplier.getSupplier().equals(supplier)){
                articleList.add(articleSupplier.getArticle());
            }
        }
        return articleList;
    }
}
