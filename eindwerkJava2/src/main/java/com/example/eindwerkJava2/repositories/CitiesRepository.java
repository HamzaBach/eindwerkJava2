package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<City, Long> {
    boolean existsCityByCityName(String cityName);

}