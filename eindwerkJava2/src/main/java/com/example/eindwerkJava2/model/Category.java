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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private String categoryAbbreviation;
    private int active = 1;

    public Category() {

    }

    public Category(String categoryName, String categoryAbbreviation) {
        this.categoryName = categoryName;
        this.categoryAbbreviation = categoryAbbreviation;
    }


    @Override
    public String toString() {
        return categoryName;
    }
}
