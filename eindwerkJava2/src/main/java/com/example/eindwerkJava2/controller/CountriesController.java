package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.repositories.CountriesRepository;
import com.example.eindwerkJava2.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @GetMapping("/country")
    public String viewCountries(Model model){
        model.addAttribute("countriesList", countriesService.getAllCountries());
        return "country";
    }
}
