package com.example.eindwerkJava2.model.dto;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderDto extends OrderSupplierDetail {
    private Double totalPriceExclVat;
    private Double totalPriceInclVat;
    private String vatRate;

    public OrderDto convertOrderSupplierDetailToOrderDto(OrderSupplierDetail orderSupplierDetail){
        this.setDeltaQuantity(orderSupplierDetail.getDeltaQuantity());
        this.setReceivedQuantity(orderSupplierDetail.getReceivedQuantity());
        this.setCurrency(orderSupplierDetail.getCurrency());
        this.setOrderSupplierDetailId(orderSupplierDetail.getOrderSupplierDetailId());
        this.setArticle(orderSupplierDetail.getArticle());
        this.setBuyPriceArticleExclVat(orderSupplierDetail.getBuyPriceArticleExclVat());
        this.setOrderlineNumber(orderSupplierDetail.getOrderlineNumber());
        this.setOrderSupplierHeader(orderSupplierDetail.getOrderSupplierHeader());
        this.setExpectedDayOfDelivery(orderSupplierDetail.getExpectedDayOfDelivery());
        this.setExpectedQuantity(orderSupplierDetail.getExpectedQuantity());
        return this;
    }

    public OrderSupplierDetail convertOrderDtoToOrderSupplierDetail(OrderDto this){
        OrderSupplierDetail orderSupplierDetail = new OrderSupplierDetail();
        orderSupplierDetail.setDeltaQuantity(this.getDeltaQuantity());
        orderSupplierDetail.setReceivedQuantity(this.getReceivedQuantity());
        orderSupplierDetail.setCurrency(this.getCurrency());
        orderSupplierDetail.setOrderSupplierDetailId(this.getOrderSupplierDetailId());
        orderSupplierDetail.setArticle(this.getArticle());
        orderSupplierDetail.setBuyPriceArticleExclVat(this.getBuyPriceArticleExclVat());
        orderSupplierDetail.setOrderlineNumber(this.getOrderlineNumber());
        orderSupplierDetail.setOrderSupplierHeader(this.getOrderSupplierHeader());
        orderSupplierDetail.setExpectedDayOfDelivery(this.getExpectedDayOfDelivery());
        orderSupplierDetail.setExpectedQuantity(this.getExpectedQuantity());
        return orderSupplierDetail;
    }
}
