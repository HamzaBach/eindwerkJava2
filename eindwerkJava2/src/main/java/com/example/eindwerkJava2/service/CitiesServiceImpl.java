package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.City;
import com.example.eindwerkJava2.repositories.CitiesRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService {


    @Autowired
    private CitiesRepository citiesRepository;

    @Override
    public SuccessEvaluator<City> getAllCities(){
        SuccessEvaluator<City> retrievedCities = new SuccessEvaluator<>();
        List<City> citiesList = this.citiesRepository.findAll();
        if(citiesList.size()>0){
            retrievedCities.setEntities(citiesList);
            retrievedCities.setIsSuccessfull(true);
        } else {
            retrievedCities.setIsSuccessfull(false);
            retrievedCities.setMessage("No cities found within the database.");
        }
        return retrievedCities;
    }

    @Override
    public void saveCity(City city) {
        citiesRepository.save(city);
    }

    @Override
    public City findById(Long cityId) {
        return citiesRepository.findById(cityId).get();
    }

}