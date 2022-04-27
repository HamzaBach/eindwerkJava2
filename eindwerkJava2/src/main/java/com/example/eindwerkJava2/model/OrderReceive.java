package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table(name = "order_receive")
public class OrderReceive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount_expected")
    private int amountExpected;
    @Column(name = "amount_received")
    private int amountReceived;
    @Column(name="order_number")
    private String orderNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmountExpected() {
        return amountExpected;
    }

    public void setAmountExpected(int amountExpected) {
        this.amountExpected = amountExpected;
    }

    public int getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(int amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

}
