package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table
public class TransactionType {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Long transactionTypeId;
    private String transactionTypeName;
    private Double transactionTypeFactor;

    public TransactionType(){}

    public TransactionType(String transactionTypeName, Double transactionTypeFactor) {
        this.transactionTypeName = transactionTypeName;
        this.transactionTypeFactor = transactionTypeFactor;
    }

    //Getters & Setters:

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public Double getTransactionTypeFactor() {
        return transactionTypeFactor;
    }

    public void setTransactionTypeFactor(Double transactionTypeFactor) {
        this.transactionTypeFactor = transactionTypeFactor;
    }
}
