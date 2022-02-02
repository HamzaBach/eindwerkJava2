package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.SupplierRepository;
import com.example.eindwerkJava2.service.CitiesService;
import com.example.eindwerkJava2.service.CountriesService;
import com.example.eindwerkJava2.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CitiesService citiesService;
    @Autowired
    private CountriesService countriesService;

    @GetMapping("/supplier")
    public String viewSuppliers(Model model){
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        return "supplier";
    }

    @GetMapping("/showNewSupplierForm")
    public String showNewSupplierForm(Model model){
        Supplier supplier = new Supplier();

        model.addAttribute("supplier", new Supplier());
        model.addAttribute("citiesList", citiesService.getAllCities());
        model.addAttribute("countriesList", countriesService.getAllCountries());
        return "form_supplier";
    }

    @PostMapping("/saveSupplier")
    public String saveSupplier(@ModelAttribute("supplier") Supplier supplier){
        this.supplierService.saveSupplier(supplier);
        return "redirect:/supplier";
    }


    @GetMapping("editSupplier/{supplierId}")
    public String showEditSupplierForm(@PathVariable("supplierId") Long supplierId, Model model){
        Supplier supplier = supplierService.findById(supplierId).get();
        model.addAttribute("supplier", supplier);
        model.addAttribute("citiesList", citiesService.getAllCities());
        model.addAttribute("countriesList", countriesService.getAllCountries());
        return "form_supplier";
    }








}