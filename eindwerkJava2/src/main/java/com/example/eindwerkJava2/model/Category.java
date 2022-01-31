package com.example.eindwerkJava2.model;


import javax.persistence.*;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue
    private Long categoryId;
    private String categoryName;

    public Category(Long categoryID,String categoryName)
    {
        this.categoryId = categoryID;
        this.categoryName = categoryName;
    }

    public Category() {

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
