package com.example.eindwerkJava2.model.dto;

import lombok.Getter;

@Getter
public class ArticleDto {
    public String article;
    public String category;
    public double price;
    public String articleImage;

    public ArticleDto(String article, String category, double price, String articleImage) {
        this.article = article;
        this.category = category;
        this.price = price;
        this.articleImage = articleImage;
    }

}
