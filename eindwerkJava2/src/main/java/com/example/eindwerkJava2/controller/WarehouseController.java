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

    @Autowired
    private WarehouseService warehouseService;


    @GetMapping(path = "/warehouse")
    public String listWarehouses(Model model) {
        List<Warehouse> listWarehouses = warehouseService.activeWarehouses();
        model.addAttribute("listWarehouses", listWarehouses);
        return "warehouse";
    }

    @GetMapping(path = "/newWarehouse")
    public String showNewLocationForm(Model model){
        model.addAttribute("warehouse", new Warehouse());
        return "form_warehouse";
    }


    @PostMapping("/warehouse/save")
    public String saveLocation(Warehouse warehouse) {
        warehouseService.addWarehouse(warehouse);
        return "redirect:/warehouse";
    }


    @GetMapping("/editWarehouse/{warehouseId}")
    public String editWarehouse(@PathVariable("warehouseId") Long warehouseId, Model model) {
        Warehouse warehouse = warehouseService.findWarehouse(warehouseId);
        model.addAttribute("warehouse",warehouse);
    return "form_warehouse";
    }

    @GetMapping("deleteWarehouse/{warehouseId}")
    public String deleteWarehouse(@PathVariable("warehouseId") Long warehouseId, Model model){
        Warehouse warehouse = warehouseService.findWarehouse(warehouseId);
        this.warehouseService.deleteWarehouse(warehouse);
        return "redirect:/warehouse";
    }

}