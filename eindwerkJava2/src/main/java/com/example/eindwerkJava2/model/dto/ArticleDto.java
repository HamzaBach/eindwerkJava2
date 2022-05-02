package com.example.eindwerkJava2.model.dto;

import lombok.Getter;

@Getter
public class ArticleDto {
    public String article;
    public String category;
    public double price;

    public ArticleDto(String article, String category, double price) {
        this.article = article;
        this.category = category;
        this.price = price;
    }

}
