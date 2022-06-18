package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.service.WarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/warehouses")
public class WarehouseRestController {


    private final WarehouseService warehouseService;

    public WarehouseRestController(WarehouseService warehouseService){
        this.warehouseService = warehouseService;
            }

    @GetMapping("/stores")
    public List<String> getStores(){
        return warehouseService.Stores();
    }

}
