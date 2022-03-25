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

/**
 * The type Location controller.
 * @author Sebastiaan
 */
@Controller
public class LocationController {

@Autowired
private LocationService locationService;
@Autowired
private WarehouseService warehouseService;


    /**
     * Takes all locations and sends them to the webpage via 'listLocations'
     * Presents the 'location' page
     *
     * @param model model
     * @return the 'location' page
     */
    @GetMapping(path = "/location")
    public String listLocations(Model model){
        List<Location> listLocations = locationService.activeLocations();
        model.addAttribute("listLocations",listLocations);
        return "location";}

    /**
     * Shows the form_location page
     * creates a new Location object that will be saved
     * Collects all active warehouses and sends them to the webpage so user can select a warehouse.
     * @see com.example.eindwerkJava2.model.Warehouse
     * @param model the model
     * @return the form_location page
     */
    @GetMapping(path = "/new/location")
    public String showNewLocationForm(Model model){
        model.addAttribute("location", new Location());
        model.addAttribute("warehouseList", warehouseService.activeWarehouses());
        return "/forms/form_location";
    }

    /**
     * Saves a new location or edited location whose data has been entered on the form_location webpage.
     *
     * @param location the location
     * @return redirects to the 'location' page
     */
    @PostMapping("/location/save")
    public String saveLocation(Location location){
        locationService.addLocation(location);
    return "redirect:/location";
    }


    /**
     * Send the user to the form_location page to edit the location of which the locationId has been selected
     *
     * Collects all active warehouses and sends them to the webpage so user can select a warehouse.
     * @see com.example.eindwerkJava2.model.Warehouse
     * @param locationId the location id
     * @param model      the model
     * @return the form_location page
     */
    @GetMapping("edit/location/{locationId}")
    public String editLocation(@PathVariable("locationId") Long locationId, Model model){
        Location location = locationService.findByLocationId(locationId);
        model.addAttribute("location", location);
        model.addAttribute("warehouseList", warehouseService.activeWarehouses());
        return "/forms/form_location";}

    /**
     * Delete a location.
     * Doesn't actually delete a location but sets the locations 'Active' attribute to 0
     *
     * @param locationId the location id
     * @param model      the model
     * @return redirects to the 'location' webpage
     */
    @GetMapping("delete/location/{locationId}")
    public String deleteLocation(@PathVariable("locationId") Long locationId, Model model){
        Location location = locationService.findByLocationId(locationId);
        this.locationService.deleteLocation(location);
        return "redirect:/location";
    }





}
