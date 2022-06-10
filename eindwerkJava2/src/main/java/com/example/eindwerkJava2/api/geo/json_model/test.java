package com.example.eindwerkJava2.api.geo.json_model;

import com.example.eindwerkJava2.api.geo.ApiCountriesCities;

import java.io.IOException;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException, InterruptedException {
        ApiCountriesCities testApi = new ApiCountriesCities();
        List<Country_json> countries= testApi.getCountries();
        List<City_json> cities = testApi.getCities("Limburg");//Input here the province name
        List<State_json> states = testApi.getStates("Belgium");
    }
}
