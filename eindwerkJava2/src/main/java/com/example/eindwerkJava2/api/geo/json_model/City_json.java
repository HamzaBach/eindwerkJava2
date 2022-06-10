package com.example.eindwerkJava2.api.geo.json_model;

import com.example.eindwerkJava2.model.City;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class City_json {
    private String city_name;

    public City convertToCity(){
        City city = new City();
        city.setCityName(this.city_name);
        return city;
    }
}
