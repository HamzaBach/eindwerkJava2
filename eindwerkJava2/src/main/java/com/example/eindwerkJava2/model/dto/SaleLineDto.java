package com.example.eindwerkJava2.model.dto;

import lombok.Getter;

@Getter
public class SaleLineDto {
    private String articleName;
    private int quantity;
    private int unitPrice;
    private double totalLinePrice;

    public SaleLineDto(String articleName, int quantity, int unitPrice) {
        this.articleName = articleName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalLinePrice = quantity* unitPrice;
    }
}
