package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table(name="Countries")
public class Countries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long countryId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name="iso")
    private String iso;



    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

}
