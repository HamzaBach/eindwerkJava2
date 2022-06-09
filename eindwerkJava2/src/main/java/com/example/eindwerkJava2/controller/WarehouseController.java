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

/**
 * The type Warehouse controller.
 * @author Sebastiaan
 */
@Controller
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private LocationService locationService;


    /**
     * takes all active warehouses and sends them to the webpage 'warehouse'.
     * Presents the 'warehouse' page
     *
     * @param model the model
     * @return the 'warehouse' page
     */
    @GetMapping(path = "/warehouse")
    public String listWarehouses(Model model) {
        List<Warehouse> listWarehouses = warehouseService.activeWarehouses();
        model.addAttribute("listWarehouses", listWarehouses);
        return "warehouse";
    }

    /**
     * Shows the 'form_warehouse' page
     * creates a new warehouse object that will be saved.
     *
     * @param model the model
     * @return the 'form_warehouse' page
     */
    @GetMapping(path = "/new/warehouse")
    public String showNewLocationForm(Model model){
        model.addAttribute("warehouse", new Warehouse());
        return "/forms/form_warehouse";
    }


    /**
     * Saves a new warehouse or edited warehouse whose data has been entered on the form_warehouse webpage.
     *
     * @param warehouse the warehouse
     * @return redirects to the 'warehouse' page
     */
    @PostMapping("/warehouse/save")
    public String saveLocation(Warehouse warehouse) {
        warehouseService.addWarehouse(warehouse);
        return "redirect:/warehouse";
    }


    /**
     * Send the user to the form_warehouse page to edit the warehouse of which the warehouseId has been selected
     *
     * @param warehouseId the warehouse id
     * @param model       the model
     * @return the 'form_warehouse' page
     * @throws Exception the exception occurs when the ID does not exist
     */
    @GetMapping("/edit/warehouse/{warehouseId}")
    public String editWarehouse(@PathVariable("warehouseId") Long warehouseId, Model model) throws Exception {
        Warehouse warehouse = warehouseService.findWarehouse(warehouseId);
        model.addAttribute("warehouse",warehouse);
        model.addAttribute("locationsInWarehouse",locationService.getLocationsOfWarehouse(warehouseId));
    return "/forms/form_warehouse";
    }

    /**
     * Delete a warehouse.
     * Doesn't actually delete a warehouse but sets the warehouse 'Active' attribute to 0
     *
     * @param warehouseId the warehouse id
     * @param model       the model
     * @return redirects to the 'warehouse' webpage
     * @throws Exception the exception occurs when the ID does not exist
     */
    @GetMapping("delete/warehouse/{warehouseId}")
    public String deleteWarehouse(@PathVariable("warehouseId") Long warehouseId, Model model) throws Exception {
        Warehouse warehouse = warehouseService.findWarehouse(warehouseId);
        this.warehouseService.deleteWarehouse(warehouse);
        return "redirect:/warehouse";
    }

}