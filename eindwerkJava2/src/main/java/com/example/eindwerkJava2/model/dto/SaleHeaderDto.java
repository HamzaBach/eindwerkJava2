package com.example.eindwerkJava2.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SaleHeaderDto {
    private Long saleHeaderId;
    private String salesPerson;
    private LocalDateTime dateOfSale;
    private List<SaleLineDto> saleLineDtos;
    private double totalPrice;

    public SaleHeaderDto(Long saleHeaderId,String salesPerson, LocalDateTime dateOfSale, List<SaleLineDto> saleLineDtos, double totalPrice ) {
        this.saleHeaderId = saleHeaderId;
        this.salesPerson = salesPerson;
        this.dateOfSale = dateOfSale;
        this.saleLineDtos = saleLineDtos;
        this.totalPrice = totalPrice;

    }
}
