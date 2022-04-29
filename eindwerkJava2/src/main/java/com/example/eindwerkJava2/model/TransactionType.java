package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class TransactionType {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Long transactionTypeId;
    private String transactionTypeName;
    private Double transactionTypeFactor;

    public TransactionType() {
    }

    public TransactionType(String transactionTypeName, Double transactionTypeFactor) {
        this.transactionTypeName = transactionTypeName;
        this.transactionTypeFactor = transactionTypeFactor;
    }


}
