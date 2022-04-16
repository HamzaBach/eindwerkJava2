package com.example.eindwerkJava2.model;

import javax.persistence.*;

/**
 * This is the model for Stock.
 *
 * @author Johan.
 */

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
    private Article article;

    private double amount;

    @Column(name = "activeStock")
    private int activeStock = 1;

    /**
     * Initiates a new Stock.
     *
     * @param stockId           ID of the stock in the database.
     * @param location          link to the location table.
     * @param articleSupplier   link to table article, column articleSupplier.
     * @param amount            link to the amount table.
     * @param activeStock       stock (active or  inactive (=deleted)).
     */
    public Stock(Long stockId, Location location, Article articleSupplier, double amount, int activeStock) {
        this.stockId = stockId;
        this.location = location;
        this.article = articleSupplier;
        this.amount = amount;
        this.activeStock = activeStock;
    }

    /**
     * Instantiates a new Stock.
     */
    public Stock(){

    }

    /**
     * Get stock id long.
     *
     * @return the stockId.
     */
    public Long getStockId() {
        return stockId;
    }

    /**
     * Sets stock id.
     *
     * @param stockId the stock id
     */
    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets article.
     *
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Sets article.
     *
     * @param article the article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets activeStock.
     *
     * @return the activeStock
     */
    public int getActiveStock() {
        return activeStock;
    }

    /**
     * Sets activeStock.
     *
     * @param activeStock the activeStock
     */
    public void setActiveStock(int activeStock) {
        this.activeStock = activeStock;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
