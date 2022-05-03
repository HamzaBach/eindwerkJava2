package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table()
public class ArticleSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleSupplierId;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "supplier")
    private Supplier supplier;

    @Column(name = "partnr")
    private String partnr;

    @Column(name = "sales_price")
    private double salesPrice;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "active")
    private int activeArticleSupplier = 1;

    @Column(name = "eta")
    private int ETA;

    public ArticleSupplier() {
    }

    public ArticleSupplier(Article article, Supplier supplier, String partnr,
                           double salesPrice, double purchasePrice, int activeArticleSupplier) {
        this.article = article;
        this.supplier = supplier;
        this.partnr = partnr;
        this.salesPrice = salesPrice;
        this.purchasePrice = purchasePrice;
        this.activeArticleSupplier = activeArticleSupplier;
    }


}
