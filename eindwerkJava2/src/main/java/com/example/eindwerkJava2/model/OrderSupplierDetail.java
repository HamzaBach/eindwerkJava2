package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "orderSupplierDetail")
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


    @Column(name="expected_quantity")
    private int expectedQuantity;
    @Column(name="delta_quantity")
    private int deltaQuantity;
    @Column(name="received_quantity")
    private int receivedQuantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedDayOfDelivery;

    @Column(name = "orderline_number")
    private String orderlineNumber;

    public OrderSupplierDetail() {
    }

    public OrderSupplierDetail(Article article, int quantity, LocalDate expectedDayOfDelivery, String orderlineNumber) {
        this.article = article;
        this.expectedQuantity = quantity;
        this.expectedDayOfDelivery = expectedDayOfDelivery;
        this.orderlineNumber = orderlineNumber;
    }


}
