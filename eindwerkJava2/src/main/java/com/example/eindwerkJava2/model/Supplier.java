package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table(name="supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @Column(name = "sup_name")
    private String supplierName;

    @Column(name = "adress")
    private String adress;

    @ManyToOne
    @JoinColumn(name = "city")
    private Cities city;

    @ManyToOne
    @JoinColumn(name = "country")
    private Countries country;

    public Supplier() {
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries country) {
        this.country = country;
    }
}

