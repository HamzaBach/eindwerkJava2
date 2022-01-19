package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table
public class Article {

    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Long articleId;
    private String articleName;
    private String articleDescription;
    private Long categoryId;//TODO: refer to dependent classes!!!
    private Long supplierId;//TODO: refer to dependent classes!!!
    private String articleBarcode;
    private String thumbNail;

    public Article() {
    }
    public Article( String articleName, String articleDescription,
    Long categoryId, Long supplierId, String thumbNail){
        this.articleName=articleName;
        this.articleDescription=articleDescription;
        this.categoryId=categoryId;
        this.supplierId=supplierId;
        this.thumbNail=thumbNail;
    }



}
