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
    @JoinColumn(name="locationIdFrom")
    private Location locationFrom;
    @ManyToOne
    @JoinColumn(name="locationIdTo")
    private Location locationTO;

//     2 nieuw properties -> warehouse -> From & TO
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
