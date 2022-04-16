package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.util.Objects;

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

    @Column(name = "active")
    private int activeSupplier = 1;

    @ManyToOne
    @JoinColumn(name = "city")
    private Cities city;

    @ManyToOne
    @JoinColumn(name = "country")
    private Countries country;

    public Supplier() {
    }
    public Supplier(String supplierName, String adress,
                    Cities city, Countries country, int activeSupplier){
        this.supplierName=supplierName;
        this.adress=adress;
        this.city=city;
        this.country=country;
        this.activeSupplier=activeSupplier;
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

    public int getActiveSupplier() {
        return activeSupplier;
    }

    public void setActiveSupplier(int activeSupplier) {
        this.activeSupplier = activeSupplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(supplierId, supplier.supplierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId);
    }
}

