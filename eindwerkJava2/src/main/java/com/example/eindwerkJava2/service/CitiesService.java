package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.City;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;

public interface CitiesService {
    SuccessEvaluator<City> getAllCities();
    //TODO: add logic for saving!
   void saveCity(City city);

    City findById(Long cityId);
}

