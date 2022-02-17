package com.example.eindwerkJava2.model;

import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name="supplierId")
    private Supplier supplier;
    private String articleBarcode;
    private int activeArticle;
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] articleImage;
    @OneToMany(mappedBy = "article")
    private List<Stock> stock;


    public Article() {
        this.activeArticle=1;
    }
    public Article(String articleName, String articleDescription,
                   Category category, Supplier supplier, byte[] articleImage, int activeArticle){
        this.articleName=articleName;
        this.articleDescription=articleDescription;
        this.category=category;
        this.supplier = supplier;
        this.articleImage = articleImage;
        this.activeArticle = activeArticle;
    }

    public Article(String articleName, String articleDescription,
                    Category category, Supplier supplier){
        this.articleName=articleName;
        this.articleDescription=articleDescription;
        this.category=category;
        this.supplier = supplier;
        this.activeArticle = 1;
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

    public int getActiveArticle() {
        return activeArticle;
    }

    public void setActiveArticle(int activeArticle) {
        this.activeArticle = activeArticle;
    }

    public void setArticleImage(byte[] articleImage) {
        this.articleImage = articleImage;
    }

    public byte[] getArticleImage() {

        return this.articleImage;
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getArticleBarcode() {
        return articleBarcode;
    }

    public void setArticleBarcode(String articleBarcode) {
        this.articleBarcode = articleBarcode;
    }
}
