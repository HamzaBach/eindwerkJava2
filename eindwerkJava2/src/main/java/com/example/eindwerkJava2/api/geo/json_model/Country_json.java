package com.example.eindwerkJava2.api.geo.json_model;

import com.example.eindwerkJava2.model.Country;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Country_json {
    private String country_name;
    private String country_short_name;
    private String country_phone_code;

    public Country convertToCountry(){
        Country country = new Country();
        country.setCountryName(this.country_name);
        country.setIso(this.country_short_name);
        return country;
    }
}
