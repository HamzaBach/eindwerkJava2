package com.example.eindwerkJava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sale_line")
public class SaleLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleLineId;
    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "unit_price")
    private int unitPrice;
    @ManyToOne
    @JoinColumn(name = "sale_header")
    private SaleHeader saleHeader;

    public SaleLine(Article article, int quantity, int unitPrice) {
        this.article = article;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
