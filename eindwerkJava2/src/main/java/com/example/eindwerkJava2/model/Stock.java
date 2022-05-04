package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * This is the model for Stock.
 *
 * @author Johan.
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "articleId")
    private Article article;

    private double amount;

    @Column(name = "activeStock")
    private int activeStock = 1;

    /**
     * Initiates a new Stock.
     *
     * @param stockId     ID of the stock in the database.
     * @param location    link to the location table.
     * @param article     link to table article, column articleSupplier.
     * @param amount      link to the amount table.
     * @param activeStock stock (active or  inactive (=deleted)).
     */
    public Stock(Long stockId, Location location, Article article, double amount, int activeStock) {
        this.stockId = stockId;
        this.location = location;
        this.article = article;
        this.amount = amount;
        this.activeStock = activeStock;
    }

    /**
     * Instantiates a new Stock.
     */
    public Stock() {

    }


}
