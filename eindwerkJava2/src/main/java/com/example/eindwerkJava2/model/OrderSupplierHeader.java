package com.example.eindwerkJava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "OrderSupplierHeader")
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


    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "date_order_closed")
    private LocalDate dateOrderClosed;

    @Column(name = "date_order_received")
    private LocalDate dateOrderReceived;


    public OrderSupplierHeader(Supplier supplier, LocalDate dateOfOrder) {
        this.supplier = supplier;
        this.dateOfOrder = dateOfOrder;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSupplierHeader that = (OrderSupplierHeader) o;
        return Objects.equals(orderSupplierId, that.orderSupplierId) && Objects.equals(supplier, that.supplier) && Objects.equals(dateOfOrder, that.dateOfOrder) && Objects.equals(orderNumber, that.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderSupplierId, supplier, dateOfOrder, orderNumber);
    }
}
