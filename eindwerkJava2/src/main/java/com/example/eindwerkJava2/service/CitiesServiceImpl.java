package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Cities;
import com.example.eindwerkJava2.repositories.CitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService {


    @Autowired
    private CitiesRepository citiesRepository;

    @Override
    public List<Cities> getAllCities(){
        return citiesRepository.findAll();
    }
}