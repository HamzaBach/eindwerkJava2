package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Supplier;
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




    @GetMapping(path = "/location")
    public String listLocations(Model model){
        List<Location> listLocations = locationService.activeLocations();
        model.addAttribute("listLocations",listLocations);
        return "location";}

    @GetMapping(path = "/newLocation")
    public String showNewLocationForm(Model model){
        model.addAttribute("location", new Location());
        model.addAttribute("warehouseList", warehouseService.activeWarehouses());
        return "form_location";
    }

    @PostMapping("/location/save")
    public String saveLocation(Location location){
        locationService.addLocation(location);
    return "redirect:/location";
    }


    @GetMapping("editLocation/{locationId}")
    public String editLocation(@PathVariable("locationId") Long locationId, Model model){
        Location location = locationService.findByLocationId(locationId);
        model.addAttribute("location", location);
        model.addAttribute("warehouseList", warehouseService.getAllWarehouses());
        return "form_location";}

    @GetMapping("deleteLocation/{locationId}")
    public String deleteLocation(@PathVariable("locationId") Long locationId, Model model){
        Location location = locationService.findByLocationId(locationId);
        this.locationService.deleteLocation(location);
        return "redirect:/location";
    }





}
