package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.service.LocationService;
import com.example.eindwerkJava2.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "eindwerk/warehouse")
public class WarehouseController {
    private WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public List<Warehouse> getWarehouses() {
        return warehouseService.getAllWarehouses();
    }


}