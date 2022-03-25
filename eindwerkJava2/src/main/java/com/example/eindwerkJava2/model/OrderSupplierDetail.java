package com.example.eindwerkJava2.model;

import javax.persistence.*;


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

    // Naming nog na checken

    @Column(name="quanity")
    private int quantity;


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
}
