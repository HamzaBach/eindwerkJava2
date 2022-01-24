package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class CitiesController {
    @Autowired
    private CitiesService citiesService;

    @GetMapping("/city")
    public String viewCities(Model model){
        model.addAttribute("citiesList", citiesService.getAllCities());
        return "cities";
    }
}
