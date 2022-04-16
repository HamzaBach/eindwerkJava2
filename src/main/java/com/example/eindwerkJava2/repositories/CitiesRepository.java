package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<Cities, Long> {
    boolean existsCitiesByCityName(String cityName);

}