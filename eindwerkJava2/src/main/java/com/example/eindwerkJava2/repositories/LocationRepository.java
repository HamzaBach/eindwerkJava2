package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

Location findByLocationId(Long locationId);


    @Query("SELECT u FROM Location u WHERE u.activeLocation = 1")
    List<Location> activeLocations();


}
