package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * The type Location service.
 * @author Sebastiaan
 */
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    /**
     * Instantiates a new Location service.
     *
     * @param locationRepository the location repository
     */
    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Find location by id.
     *
     * @param locationId the location id
     * @return the location
     */
    public Location findByLocationId(Long locationId){
        return this.locationRepository.findByLocationId(locationId);
    }

    /**
     * Gets all locations.
     *
     * @return  all locations
     */
    public List<Location> getAllLocations() {
        return this.locationRepository.findAll();
    }

    /**
     * Add a location.
     *
     * @param location the location
     */
    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    /**
     * Delete a location.
     *
     * @param location the location
     */
    public void deleteLocation(Location location){
        location.setActiveLocation(0);
        this.locationRepository.save(location);
    }

    /**
     * List of all active locations.
     *
     * @return the list
     */
    public List<Location> activeLocations(){
        return this.locationRepository.activeLocations();
    }


}






