package com.example.eindwerkJava2.model;

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
    private LocalDate dateOfOrder;
    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "date_order_closed")
    private LocalDate dateOrderClosed;

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

    public int getOrderNumber() {
        return orderNumber;
    }

}
