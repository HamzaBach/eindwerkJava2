package com.example.eindwerkJava2.model;

import javax.persistence.*;
@Entity
@Table(name="article_supplier")
public class Article_Supplier {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long article_supplierId;

        @ManyToOne
        @JoinColumn(name = "article")
        private Article article;

        @ManyToOne
        @JoinColumn(name = "supplier")
        private Supplier supplier;

        @Column(name = "price")
        private double price;

        public Article_Supplier() {
        }

        public long getArticle_supplierId() {
                return article_supplierId;
        }

        public void setArticle_supplierId(long article_supplierId) {
                this.article_supplierId = article_supplierId;
        }

        public Article getArticle() {
                return article;
        }

        public void setArticle(Article article) {
                this.article = article;
        }

        public Supplier getSupplier() {
                return supplier;
        }

        public void setSupplier(Supplier supplier) {
                this.supplier = supplier;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }
}
