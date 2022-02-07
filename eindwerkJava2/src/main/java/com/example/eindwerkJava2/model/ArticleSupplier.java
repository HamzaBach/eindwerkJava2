package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table()
public class ArticleSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long articleSupplierId;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private Supplier supplier;

    @Column(name = "partnr")
    private String partnr;

    @Column(name = "price")
    private double price;

    public ArticleSupplier() {
    }

    public long getArticleSupplierId() {
        return articleSupplierId;
    }

    public void setArticleSupplierId(long articleSupplierId) {
        this.articleSupplierId = articleSupplierId;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getPartnr() {
        return partnr;
    }

    public void setPartnr(String partnr) {
        this.partnr = partnr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
