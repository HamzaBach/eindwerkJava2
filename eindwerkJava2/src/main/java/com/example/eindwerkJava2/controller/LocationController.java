package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.service.LocationService;
import com.example.eindwerkJava2.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LocationController {

@Autowired
private LocationService locationService;
@Autowired
private WarehouseService warehouseService;


    public LocationController(LocationService locationService, WarehouseService warehouseService)
    {this.locationService = locationService;
    this.warehouseService = warehouseService;}


    @GetMapping(path = "/")
    public String hello(){
        return "index";
    }

    @GetMapping(path = "/location")
    public String listLocations(Model model){
        List<Location> listLocations = locationService.getAllLocations();
        model.addAttribute("listLocations",listLocations);
        return "location";}

    @GetMapping(path = "/newLocation")
    public String showNewLocationForm(Model model){
        model.addAttribute("location", new Location());
        model.addAttribute("warehouseList", warehouseService.getAllWarehouses());
        return "newLocationForm";
    }

    @PostMapping("/location/save")
    public String saveLocation(Location location){
        locationService.addLocation(location);
    return "redirect:/location";
    }







}
