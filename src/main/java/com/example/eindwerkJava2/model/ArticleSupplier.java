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

    @Column(name = "sales_price")
    private double salesPrice;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "active")
    private int activeArticleSupplier =1;

    @Column(name = "eta")
    private int ETA;



    public ArticleSupplier() {
    }
    public ArticleSupplier(Article article, Supplier supplier, String partnr,
                           double salesPrice, double purchasePrice, int activeArticleSupplier){
        this.article=article;
        this.supplier=supplier;
        this.partnr=partnr;
        this.salesPrice =salesPrice;
        this.purchasePrice=purchasePrice;
        this.activeArticleSupplier=activeArticleSupplier;
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

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getETA() {
        return ETA;
    }

    public void setETA(int ETA) {
        this.ETA = ETA;
    }

    public int getActiveArticleSupplier() {
        return activeArticleSupplier;
    }

    public void setActiveArticleSupplier(int activeArticleSupplier) {
        this.activeArticleSupplier = activeArticleSupplier;
    }
}
