package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@EqualsAndHashCode
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


}
