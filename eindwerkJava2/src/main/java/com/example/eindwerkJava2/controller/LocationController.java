package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "eindwerk/location")
public class LocationController {
private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService)
    {this.locationService = locationService;}

    @GetMapping(path = "/all")
    public List<Location> getLocations()
    {return locationService.getAllLocations();}


    @PostMapping(path = "{locationId}/{locationName}/{warehouse}/{singleStorage}")
    public void saveLocation(@PathVariable("locationId") int locationId, @PathVariable("locationName") String locationName,
                             @PathVariable("warehouse") Warehouse warehouse, @PathVariable("singleStorage") Boolean singleStorage )
    {locationService.addLocation(locationId,locationName,warehouse,singleStorage);}





}
