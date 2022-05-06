package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cityId;

    @Column(name = "city_zipcode")
    private int cityZipcode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_province")
    private String cityProvince;

    /**
     *
     */
    public City() {
    }


    public City(int cityZipcode, String cityName, String cityProvince) {
        this.cityZipcode = cityZipcode;
        this.cityName = cityName;
        this.cityProvince = cityProvince;
    }

    @Override
    public String toString() {
        return cityName;
    }
}