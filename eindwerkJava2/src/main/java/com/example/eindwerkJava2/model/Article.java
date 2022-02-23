package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This class represents an article object
 * An article has a name, description, category, article supplier, barcode, image
 */


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
    @Transient
    @OneToMany
    @JoinColumn(name="articleSupplierId", nullable = true)
    private List<ArticleSupplier> articleSuppliersList;
    @OneToOne
    private ArticleSupplier articleSupplier;
    private String articleBarcode;
    private int activeArticle;
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] articleImage;
/*    @OneToMany(mappedBy = "article")
    private List<Stock> stock;*/


    public Article() {
        this.activeArticle=1;
    }
    public Article(String articleName, String articleDescription,
                   Category category, byte[] articleImage, int activeArticle){
        this.articleName=articleName;
        this.articleDescription=articleDescription;
        this.category=category;
        this.articleImage = articleImage;
        this.activeArticle = activeArticle;
    }

    public Article(String articleName, String articleDescription,
                    Category category){
        this.articleName=articleName;
        this.articleDescription=articleDescription;
        this.category=category;
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

    public ArticleSupplier getArticleSupplier() {
        return articleSupplier;
    }

    public void setArticleSupplier(ArticleSupplier articleSupplier) {
        this.articleSupplier = articleSupplier;
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

    public List<ArticleSupplier> getArticleSuppliersList() {
        return articleSuppliersList;
    }

    public void setArticleSuppliersList(List<ArticleSupplier> articleSuppliersList) {
        this.articleSuppliersList = articleSuppliersList;
    }

    public String getArticleBarcode() {
        return articleBarcode;
    }

    public void setArticleBarcode(String articleBarcode) {
        this.articleBarcode = articleBarcode;
    }

}
