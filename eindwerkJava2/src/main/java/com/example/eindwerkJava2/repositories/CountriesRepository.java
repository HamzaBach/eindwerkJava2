package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountriesRepository extends JpaRepository<Country, Long> {
    boolean existsCountriesByCountryName(String countryName);

}