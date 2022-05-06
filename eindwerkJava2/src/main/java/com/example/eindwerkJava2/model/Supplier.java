package com.example.eindwerkJava2.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "supplier")
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
    private City city;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    public Supplier() {
    }

    public Supplier(String supplierName, String adress,
                    City city, Country country, int activeSupplier) {
        this.supplierName = supplierName;
        this.adress = adress;
        this.city = city;
        this.country = country;
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

