package com.example.eindwerkJava2.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String categoryAbbreviation;
    private int active = 1;

    @ManyToOne
    @JoinColumn(name = "vatId")
    private Vat vat;


    public Category(String categoryName, String categoryAbbreviation) {
        this.categoryName = categoryName;
        this.categoryAbbreviation = categoryAbbreviation;
    }
    public Category(String categoryName, String categoryAbbreviation,Vat vat) {
        this.categoryName = categoryName;
        this.categoryAbbreviation = categoryAbbreviation;
        this.vat=vat;
    }


    @Override
    public String toString() {
        return categoryName;
    }
}
