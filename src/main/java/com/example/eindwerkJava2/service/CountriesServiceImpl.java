package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Countries;
import com.example.eindwerkJava2.repositories.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService {


    @Autowired
    private CountriesRepository countriesRepository;

    @Override
    public List<Countries> getAllCountries(){
        return countriesRepository.findAll();
    }
}