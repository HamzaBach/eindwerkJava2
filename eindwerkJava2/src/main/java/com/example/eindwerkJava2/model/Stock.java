package com.example.eindwerkJava2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Stock {

    @Id
    @GeneratedValue
    private int stockId;
    private int locationId;
    private int articleId;
    private double amount;

    public Stock(int stockId, int locationId, int articleId, double amount){

        this.stockId = stockId;
        this.locationId = locationId;
        this.articleId = articleId;
        this.amount = amount;

    }

    public Stock(){

    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
