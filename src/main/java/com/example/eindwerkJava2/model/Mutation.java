package com.example.eindwerkJava2.model;

import javax.persistence.*;

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
    @JoinColumn(name="locationId")
    private Location location;
    @ManyToOne
    @JoinColumn(name="transactionTypeId")
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public Mutation(){}

    public Mutation(Article article, Double amount, TransactionType transactionType, User user) {
        this.article = article;
        this.amount = amount;
        this.transactionType = transactionType;
        this.user = user;
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
}
