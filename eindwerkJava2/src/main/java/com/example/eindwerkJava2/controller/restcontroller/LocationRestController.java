package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.service.LocationService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/locations")
public class LocationRestController {
    private final LocationService locationService;


    public LocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public String getLocations() throws JSONException {
        List<Location> allLocations = locationService.getAllLocations();

        JSONArray ja = new JSONArray();

        for (Location location : allLocations) {
            JSONObject jo = new JSONObject();
            jo.put("id", location.getLocationId());
            jo.put("locationName", location.getLocationName());
            ja.put(jo);
        }
        JSONObject json = new JSONObject();
        json.put("locations", ja);
        return json.toString();

    }
}
