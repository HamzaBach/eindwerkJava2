package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Vat;
import com.example.eindwerkJava2.service.CategoryService;
import com.example.eindwerkJava2.service.VatService;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller

public class VatController {
    @Autowired
    private final VatService vatService;


    public VatController(VatService vatService){
        this.vatService=vatService;
    }


    @GetMapping("/vat_rates")
    public String viewCategories(Model model){
        SuccessEvaluator<Vat> success = vatService.getAllActiveVats();
        if(success.getIsSuccessfull()){
            model.addAttribute("vatRatesList", success.getEntities());
        } else{
            model.addAttribute("error",success.getMessage());
        }
        return "vat_rates";
    }

    @GetMapping("/new/vat_rate")
    public String showNewVatForm(Model model) {
        SuccessEvaluator<Vat> success = vatService.getAllActiveVats();
        if (success.getIsSuccessfull()) {
            model.addAttribute("vatRatesList", success.getEntities());
            model.addAttribute("vatObject", new Vat());
        } else {
            model.addAttribute("error", success.getMessage());
        }
        return "/forms/form_vat_rate";
    }

    @PostMapping("/saveVatRate")
    public String saveVatRate(@ModelAttribute("vatObject") Vat vat, RedirectAttributes redirAttrs, Model model){
        SuccessObject isSaveSuccessfull = this.vatService.save(vat);
        if(isSaveSuccessfull.getIsSuccessfull()){
            redirAttrs.addFlashAttribute("success",isSaveSuccessfull.getMessage());
            return "redirect:/vat_rates";
        } else {
            model.addAttribute("error", isSaveSuccessfull.getMessage());
            return "/forms/form_vat_rate";
        }
    }


    @GetMapping("edit/vat_rate/{vatId}")
    public String showEditVatForm(@PathVariable("vatId") Long vatId, Model model){
        SuccessEvaluator<Vat> success = vatService.findById(vatId);
        if(success.getIsSuccessfull()){
            Vat vatRate = success.getEntity();
            model.addAttribute("vatObject", vatRate);
            model.addAttribute("vatList", vatService.getAllActiveVats());
        } else {
            model.addAttribute("error", success.getMessage());
        }
        return "/forms/form_vat_rate";
    }

    @GetMapping("delete/vat_rate/{vatId}")
    public String deleteVatRate(@PathVariable("vatId") Long vatId, RedirectAttributes redirAttrs){
        SuccessEvaluator<Vat> findVatRate = vatService.findById(vatId);
        if(findVatRate.getIsSuccessfull()){
            SuccessObject toBeDeletedVatRate = this.vatService.deleteVatRate((findVatRate.getEntity()));
            if(toBeDeletedVatRate.getIsSuccessfull()){
                redirAttrs.addFlashAttribute("success", toBeDeletedVatRate.getMessage());
            } else{
                redirAttrs.addFlashAttribute("error", toBeDeletedVatRate.getMessage());
            }
        } else {
            redirAttrs.addFlashAttribute("error", findVatRate.getMessage());
        }
        return "redirect:/vat_rates";
    }

}
