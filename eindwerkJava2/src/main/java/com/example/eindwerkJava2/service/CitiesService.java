package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.City;
import com.example.eindwerkJava2.wrappers.CitySuccess;

public interface CitiesService {
    CitySuccess getAllCities();
    //TODO: add logic for saving!
   void saveCity(City city);
    }

