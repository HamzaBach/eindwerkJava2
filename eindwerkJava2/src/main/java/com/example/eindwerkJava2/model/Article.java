package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Entity
@Table
public class Article {

    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Long articleId;
    private String articleName;
    private String articleDescription;
    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;
    private Long supplierId;//TODO: refer to dependent classes!!!
    private String articleBarcode;
    private byte[] articleImage;

    public Article() {
    }
    public Article( String articleName, String articleDescription,
    Category category, Long supplierId, byte[] articleImage){
        this.articleName=articleName;
        this.articleDescription=articleDescription;
        this.category=category;
        this.supplierId=supplierId;
        this.articleImage = articleImage;
    }

    public Article( String articleName, String articleDescription,
                    Category category, Long supplierId){
        this.articleName=articleName;
        this.articleDescription=articleDescription;
        this.category=category;
        this.supplierId=supplierId;
    }

    //Getters & Setters:
    public void setArticleImage(String articleImagePath) {
        FileInputStream fileInputStream;
        if (articleImagePath != null && !articleImagePath.isEmpty() && Files.exists(Path.of(articleImagePath))) {

            try {
                fileInputStream = new FileInputStream(articleImagePath);
                this.articleImage = fileInputStream.readAllBytes();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getArticleImage() {
        return articleImage;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategoryId(Category category) {
        this.category = category;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getArticleBarcode() {
        return articleBarcode;
    }

    public void setArticleBarcode(String articleBarcode) {
        this.articleBarcode = articleBarcode;
    }
}
