package com.example.eindwerkJava2.model;

import javax.persistence.*;


@Entity
@Table(name="Cities")
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cityId;

    @Column(name = "city_zipcode")
    private int cityZipcode;

    @Column(name = "city_name")
    private String cityName;



    @Column(name = "city_province")
    private String cityProvince;

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityProvince() {
        return cityProvince;
    }

    public void setCityProvince(String cityProvince) {
        this.cityProvince = cityProvince;
    }

    public long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityZipcode() {
        return cityZipcode ;
    }

    public void setCityZipcode(int cityZipcode) {
        this.cityZipcode = cityZipcode;
    }


}