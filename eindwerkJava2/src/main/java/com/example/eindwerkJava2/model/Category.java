package com.example.eindwerkJava2.model;


import javax.persistence.*;

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

    public Category(Long categoryID,String categoryName, String categoryAbbreviation)
    {
        this.categoryId = categoryID;
        this.categoryName = categoryName;
        this.categoryAbbreviation=categoryAbbreviation;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getCategoryAbbreviation() {
        return categoryAbbreviation;
    }

    public void setCategoryAbbreviation(String categoryAbbreviation) {
        this.categoryAbbreviation = categoryAbbreviation;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
