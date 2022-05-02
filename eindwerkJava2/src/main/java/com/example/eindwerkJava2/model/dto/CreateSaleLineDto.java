package com.example.eindwerkJava2.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSaleLineDto {
    private String barcode;
    private int quantity;
    private int unitPrice;
    private Long saleHeaderId;

    public CreateSaleLineDto(String barcode, int quantity, int unitPrice, Long saleHeaderId) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.saleHeaderId = saleHeaderId;
    }
}
