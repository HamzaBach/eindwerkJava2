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

    @Column(name = "active")
    private boolean active;

    public Supplier() {
    }
    public Supplier(String supplierName, String adress,
                    Cities city, Countries country, boolean active){
        this.supplierName=supplierName;
        this.adress=adress;
        this.city=city;
        this.country=country;
        this.active=active;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

