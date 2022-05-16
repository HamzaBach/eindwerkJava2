package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.CitiesService;
import com.example.eindwerkJava2.service.CountriesService;
import com.example.eindwerkJava2.service.SupplierService;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CitiesService citiesService;
    @Autowired
    private CountriesService countriesService;

    @GetMapping("/supplier")
    public String viewSuppliers(Model model) {
        SuccessEvaluator<Supplier> supplierSuccess = supplierService.getAllSuppliers();
        if (supplierSuccess.getIsSuccessfull()) {
            model.addAttribute("suppliersList", supplierSuccess.getEntities());
        } else {
            model.addAttribute("error", supplierSuccess.getMessage());
        }
        return "supplier";
    }

    @GetMapping("/new/supplier")
    public String showNewSupplierForm(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", new Supplier());
        model.addAttribute("citiesList", citiesService.getAllCities().getEntities());
        model.addAttribute("countriesList", countriesService.getAllCountries());
        return "/forms/form_supplier";
    }

    @GetMapping("/generatePdf")
    public String generatePdf() {
        Supplier supplier = new Supplier();
        supplierService.makePdf();
        return "redirect:/supplier";
    }

    @PostMapping("/saveSupplier")
    public String saveSupplier(@ModelAttribute("supplier") Supplier supplier, RedirectAttributes redirAttr, Model model) {
        SuccessObject isSaveSuccessful = this.supplierService.saveSupplier(supplier);
        if (isSaveSuccessful.getIsSuccessfull()) {
            redirAttr.addFlashAttribute("success", isSaveSuccessful.getMessage());
            return "redirect:/supplier";
        } else {
            model.addAttribute("error", isSaveSuccessful.getMessage());
            return "/forms/form_supplier";
        }
    }

    @GetMapping("edit/supplier/{supplierId}")
    public String showEditSupplierForm(@PathVariable("supplierId") Long supplierId, Model model) {
        SuccessEvaluator<Supplier> findSupplierSuccess = supplierService.findById(supplierId);
        Supplier supplier = findSupplierSuccess.getEntity();
        model.addAttribute("supplier", supplier);
        model.addAttribute("citiesList", citiesService.getAllCities().getEntities());
        model.addAttribute("countriesList", countriesService.getAllCountries());
        return "/forms/form_supplier";
    }

    @GetMapping("delete/supplier/{supplierId}")
    public String deleteSupplier(@PathVariable("supplierId") Long supplierId, RedirectAttributes redirAttr) {
        SuccessEvaluator<Supplier> findSupplierSuccess = supplierService.findById(supplierId);
        if (findSupplierSuccess.getIsSuccessfull()) {
            Supplier supplier = findSupplierSuccess.getEntity();
            SuccessObject toBeDeletedSupplier = this.supplierService.deleteSupplier(supplier);
            if (toBeDeletedSupplier.getIsSuccessfull()) {
                redirAttr.addFlashAttribute("success", toBeDeletedSupplier.getMessage());
            } else {
                redirAttr.addFlashAttribute("error", toBeDeletedSupplier.getMessage());
            }
        } else {
            redirAttr.addFlashAttribute("error", findSupplierSuccess.getMessage());
        }
        return "redirect:/supplier";
    }


}
