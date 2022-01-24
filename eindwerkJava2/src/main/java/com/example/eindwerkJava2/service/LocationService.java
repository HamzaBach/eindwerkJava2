package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findByLocationId(int locationId){
        return this.locationRepository.findByLocationId(locationId);
    }
    public List<Location> getAllLocations() {
        return this.locationRepository.findAll();
    }

    public void addLocation(int locationId, String locationName, Warehouse warehouse, Boolean singleLocation) {

        Location location = new Location(locationId, locationName, warehouse, singleLocation);

        locationRepository.save(location);
    }

}






