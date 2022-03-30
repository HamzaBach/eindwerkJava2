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
 * An article has an articleId, articleName, articleDescription, category, articleSyppliersList, articleSupplier, articleBarcode, activeArticle, articleImage and a articleAbbreviation.
 *
 * @author Hamza Bachiri
 * @version 1.0
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
    @JoinColumn(name = "categoryId")
    private Category category;
    @Transient
    @OneToMany
    @JoinColumn(name = "articleSupplierId", nullable = true)
    private List<ArticleSupplier> articleSuppliersList;
    @OneToOne
    @JoinColumn(name = "articleSupplierId")
    private ArticleSupplier articleSupplier;
    private String articleBarcode;
    private int activeArticle;
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] articleImage;
    private String articleAbbreviation;

    /**
     * This constructor creates an article object without any inputted attributes.
     * The newly created article is automatically set to activeArticle = 1.
     */
    public Article() {
        this.activeArticle = 1;
    }

    /**
     * This constructor creates an article object with the following parameters:
     *
     * @param articleName         The name of the article.
     * @param articleDescription  The description of the article.
     * @param category            The category of the article.
     * @param articleImage        The image of the article stored as a byte array in the object.
     * @param activeArticle       The state whether it is an active article (1) or an inactive article (0).
     * @param articleAbbreviation The abbreviation for the article (needed for generating the barcode).
     */
    public Article(String articleName, String articleDescription,
                   Category category, byte[] articleImage, int activeArticle, String articleAbbreviation) {
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.category = category;
        this.articleImage = articleImage;
        this.activeArticle = activeArticle;
        this.articleAbbreviation = articleAbbreviation;
    }

    /**
     * This constructor creates an article object with the following parameters:
     *
     * @param articleName         The name of the article.
     * @param articleDescription  The description of the article.
     * @param category            The category of the article.
     * @param articleAbbreviation The abbreviation for the article (needed for generating the barcode).
     *                            The newly created article is automatically set to activeArticle = 1.
     */
    public Article(String articleName, String articleDescription,
                   Category category, String articleAbbreviation) {
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.category = category;
        this.activeArticle = 1;
        this.articleAbbreviation = articleAbbreviation;
    }

    //Getters & Setters:

    /**
     * Setter method for setting the image as a byte array attribute in the object.
     * @param articleImagePath The path of the to be added image.
     */
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

    /**
     * Getter method for retrieving the articleAbbreviation from the object.
     * @return The articleAbbreviation.
     */
    public String getArticleAbbreviation() {
        return articleAbbreviation;
    }

    /**
     * Setter method for setting the articleAbbreviation in the object.
     * @param articleAbbreviation The to be stored articleAbbreviation.
     */
    public void setArticleAbbreviation(String articleAbbreviation) {
        this.articleAbbreviation = articleAbbreviation;
    }

    /**
     * Getter method for retrieving the articleSupplier of the object.
     * @return The articleSupplier.
     */
    public ArticleSupplier getArticleSupplier() {
        return articleSupplier;
    }

    /**
     * Setter method for setting the articleSupplier in the object.
     * @param articleSupplier The to be stored articleSupplier.
     */
    public void setArticleSupplier(ArticleSupplier articleSupplier) {
        this.articleSupplier = articleSupplier;
    }

    /**
     * Getter method for retrieving the activeArticle value of the object.
     * @return The activeArticle value.
     */
    public int getActiveArticle() {
        return activeArticle;
    }

    /**
     * Setter method for setting the activeArticle value in the object.
     * @param activeArticle The to be set activeArticle value.
     */
    public void setActiveArticle(int activeArticle) {
        this.activeArticle = activeArticle;
    }

    /**
     * Setter method for setting the articleImage as a byte array in the object.
     * @param articleImage The to be set articleImage.
     */
    public void setArticleImage(byte[] articleImage) {
        this.articleImage = articleImage;
    }

    /**
     * Getter method for retrieving the articleImage of the object.
     * @return The articleImage as a byte array.
     */
    public byte[] getArticleImage() {
        return this.articleImage;
    }

    /**
     * Getter method for retrieving the article id of the object.
     * @return The articleId.
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * Setter method for setting the article id of the object.
     * @param articleId The to be set articleId.
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * Getter method for retrieving the articleName of the object.
     * @return The articleName.
     */
    public String getArticleName() {
        return articleName;
    }

    /**
     * Setter method for setting the articleName of the object.
     * @param articleName The to be set articleName.
     */
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    /**
     * Getter method for retrieving the articleDescription of the object.
     * @return The articleDescription.
     */
    public String getArticleDescription() {
        return articleDescription;
    }

    /**
     * Setter method for setting the articleDescription of the object.
     * @param articleDescription The to be set articleDescription.
     */
    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    /**
     * Getter method for retrieving the category of the object.
     * @return The category.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Setter method for setting the category of the object.
     * @param category The to be set category.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Getter method for retrieving a list of all suppliers that deliver this article.
     * @return The articleSuppliersList.
     */
    public List<ArticleSupplier> getArticleSuppliersList() {
        return articleSuppliersList;
    }

    /**
     * Setter method for setting a list of suppliers that deliver this article.
     * @param articleSuppliersList The to be set articleSuppliersList.
     */
    public void setArticleSuppliersList(List<ArticleSupplier> articleSuppliersList) {
        this.articleSuppliersList = articleSuppliersList;
    }

    /**
     * Getter method to retrieve the articleBarcode of the object.
     * @return The articleBarcode.
     */
    public String getArticleBarcode() {
        return articleBarcode;
    }

    /**
     * Setter method for setting the articleBarcode of the object.
     * @param articleBarcode The to be set articleBarcode.
     */
    public void setArticleBarcode(String articleBarcode) {
        this.articleBarcode = articleBarcode;
    }

}
