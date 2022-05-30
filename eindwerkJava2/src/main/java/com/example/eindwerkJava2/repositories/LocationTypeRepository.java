package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LocationTypeRepository extends JpaRepository<LocationType, Long> {

}
