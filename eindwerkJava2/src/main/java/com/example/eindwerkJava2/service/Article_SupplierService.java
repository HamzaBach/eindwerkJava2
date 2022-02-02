package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Article_Supplier;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.Article_SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Article_SupplierService {
    @Autowired
    private final Article_SupplierRepository article_supplierRepository;


    public Article_SupplierService(Article_SupplierRepository article_supplierRepository) {
        this.article_supplierRepository = article_supplierRepository;
    }

    public List<Article_Supplier> getArticle_Supplier(){
        return this.article_supplierRepository.findAll();
    }

    public void saveArticleSupplier(Article_Supplier article_supplier){
        this.article_supplierRepository.save(article_supplier);
    }


}
