package com.example.eindwerkJava2.model;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="OrderSupplierHeader")
public class OrderSupplierHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSupplierId;

    @ManyToOne
    @JoinColumn(name = "supplier_supplier_id")
    private Supplier supplier;
    @Column(name = "date_of_order")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfOrder;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "date_order_closed")
    private LocalDate dateOrderClosed;


    public OrderSupplierHeader() {
    }

    public OrderSupplierHeader(Supplier supplier, LocalDate dateOfOrder) {
        this.supplier = supplier;
        this.dateOfOrder = dateOfOrder;

    }



    public LocalDate getDateOrderClosed() {
        return dateOrderClosed;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Long getOrderSupplierId() {
        return orderSupplierId;
    }

    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderSupplierId(Long orderSupplierId) {
        this.orderSupplierId = orderSupplierId;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setDateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setDateOrderClosed(LocalDate dateOrderClosed) {
        this.dateOrderClosed = dateOrderClosed;
    }
}
