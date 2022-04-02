package com.example.eindwerkJava2.model;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="orderSupplierDetail")
public class OrderSupplierDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSupplierDetailId;

    @ManyToOne
    @JoinColumn(name = "order_supplier_header_order_supplier_id")
    private OrderSupplierHeader orderSupplierHeader;

    @ManyToOne
    @JoinColumn(name = "article_article_id")
    private Article article;


    @Column(name="quantity")
    private int quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expecteddDayOfDelivery;

    @Column(name="orderline_number")
    private String orderlineNumber;

    public LocalDate getExpecteddDayOfDelivery() {
        return expecteddDayOfDelivery;
    }

    public void setExpecteddDayOfDelivery(LocalDate expecteddDayOfDelivery) {
        this.expecteddDayOfDelivery = expecteddDayOfDelivery;
    }

    public Long getOrderSupplierDetailId() {
        return orderSupplierDetailId;
    }

    public OrderSupplierHeader getOrderSupplierHeader() {
        return orderSupplierHeader;
    }

    public Article getArticle() {
        return article;
    }

    public int getQuantity() {
        return quantity;
    }


    public void setArticle(Article article) {
        this.article = article;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public void setOrderSupplierHeader(OrderSupplierHeader orderSupplierHeader) {
        this.orderSupplierHeader = orderSupplierHeader;
    }

    public void setOrderSupplierDetailId(Long orderSupplierDetailId) {
        this.orderSupplierDetailId = orderSupplierDetailId;
    }

    public String getOrderlineNumber() {
        return orderlineNumber;
    }

    public void setOrderlineNumber(String orderlineNumber) {
        this.orderlineNumber = orderlineNumber;
    }
}
