package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Countries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long> {
    boolean existsCountriesByCountryName(String countryName);

}