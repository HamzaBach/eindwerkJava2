package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long countryId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "iso")
    private String iso;

    public Country() {
    }

    public Country(String countryName, String iso) {
        this.countryName = countryName;
        this.iso = iso;
    }


}
