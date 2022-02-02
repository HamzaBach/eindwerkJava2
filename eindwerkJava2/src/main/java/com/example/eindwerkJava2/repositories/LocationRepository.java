package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

List<Location> findByLocationId(int locationId);

}
