package com.example.eindwerkJava2.model;

import javax.persistence.*;


@Entity
@Table
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name="articleId")
    private ArticleSupplier articleSupplier;

    private double amount;

    @Column(name = "activeStock")
    private int activeStock = 1;

    public Stock(Long stockId, Location location, ArticleSupplier articleSupplier, double amount, int activeStock) {
        this.stockId = stockId;
        this.location = location;
        this.articleSupplier = articleSupplier;
        this.amount = amount;
        this.activeStock = activeStock;
    }

    public Stock(){

    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArticleSupplier getArticleSupplier() {
        return articleSupplier;
    }

    public void setArticleSupplier(ArticleSupplier articleSupplier) {
        this.articleSupplier = articleSupplier;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getActiveStock() {
        return activeStock;
    }

    public void setActiveStock(int activeStock) {
        this.activeStock = activeStock;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
