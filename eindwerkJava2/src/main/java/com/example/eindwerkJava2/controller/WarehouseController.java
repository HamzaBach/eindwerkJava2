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
public class WarehouseController {
    private WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {

        this.warehouseService = warehouseService;
    }


    @GetMapping(path = "/warehouse")
    public String listWarehouses(Model model) {
        List<Warehouse> listWarehouses = warehouseService.getAllWarehouses();
        model.addAttribute("listWarehouses", listWarehouses);
        return "warehouse";
    }

    @GetMapping(path = "/newWarehouse")
    public String showNewLocationForm(Model model){
        model.addAttribute("warehouse", new Warehouse());
        return "newWarehouseForm";
    }


    @PostMapping("/warehouse/save")
    public String saveLocation(Warehouse warehouse) {
        warehouseService.addWarehouse(warehouse);
        return "redirect:/warehouse";
    }

    @GetMapping(path = "/deleteWarehouse")
    public String showdeleteWarehouse(Model model){
        model.addAttribute("warehouseList", warehouseService.getAllWarehouses());
        return "deleteWarehouse";
    }

    @PostMapping("/warehouse/delete/{warehouseId}")
    public String updateUser(@PathVariable("warehouseId") Long warehouseId, Model model) {
        Warehouse warehouse = warehouseService.findWarehouse(warehouseId);
        warehouseService.deleteWarehouse(warehouse);
    return "redirect:/warehouse";
    }



}