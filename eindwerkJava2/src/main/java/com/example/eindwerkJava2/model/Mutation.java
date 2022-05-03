package com.example.eindwerkJava2.model;

import javax.persistence.*;
import javax.persistence.criteria.From;

@Entity
@Table
public class Mutation {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Long mutationId;
    @ManyToOne
    @JoinColumn(name="articleId")
    private Article article;
    private Double amount;

    @ManyToOne
    @JoinColumn(name="locationFrom")
    private Location locationFrom;
    @ManyToOne
    @JoinColumn(name="locationTo")
    private Location locationTo;
    @ManyToOne
    @JoinColumn(name="warehouseFrom")
    private Location warehouseFrom;
    @ManyToOne
    @JoinColumn(name="warehouseTo")
    private Location warehouseTo;

//     1 locatie mag maar 1 artikel bevatten
//    artikel mag maar op 1 locatie staan in de fysieke winkel. In magazijn mogen er meerdere locaties zijn rekeninghoudnde met bovenstaande regel
//     Laatste amount stock nemen en die dan meteen aanpassen, niet itereren door mutations
    @ManyToOne
    @JoinColumn(name="transactionTypeId")
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public Mutation(){}

    public Mutation(Article article, Double amount, TransactionType transactionType,Location locationTo, Location locationFrom,Location warehouseFrom,Location warehouseTo,User user) {
        this.article = article;
        this.amount = amount;
        this.transactionType = transactionType;
        this.user = user;
        this.warehouseFrom = warehouseFrom;
        this.warehouseTo=warehouseTo;
        this.locationFrom= locationFrom;
        this.locationTo=locationTo;
    }

    //Getters & Setters:

    public Long getMutationId() {
        return mutationId;
    }

    public void setMutationId(Long mutationId) {
        this.mutationId = mutationId;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(Location locationFrom) {
        this.locationFrom = locationFrom;
    }

    public Location getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(Location locationTo) {
        this.locationTo = locationTo;
    }

    public Location getWarehouseFrom() {
        return warehouseFrom;
    }

    public void setWarehouseFrom(Location warehouseFrom) {
        this.warehouseFrom = warehouseFrom;
    }

    public Location getWarehouseTo() {
        return warehouseTo;
    }

    public void setWarehouseTo(Location warehouseTo) {
        this.warehouseTo = warehouseTo;
    }
}
