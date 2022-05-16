package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.City;
import com.example.eindwerkJava2.service.CitiesService;
import com.example.eindwerkJava2.wrappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class CitiesController {
    @Autowired
    private CitiesService citiesService;

    @GetMapping("/city")
    public String viewCities(Model model){
        SuccessEvaluator<City> retrievedCities = citiesService.getAllCities();
        if(retrievedCities.getIsSuccessfull()){
            model.addAttribute("citiesList", retrievedCities.getEntities());
        } else {
            model.addAttribute("error",retrievedCities.getMessage());
        }
        return "cities";
    }

    @GetMapping("/new/city")
    public String showNewCityForm(Model model) {
        model.addAttribute("city", new City());
        return "/forms/form_city";
    }

    @PostMapping("/saveCity")
    public String saveArticle(@ModelAttribute("city") City city) throws IOException {
        this.citiesService.saveCity(city);
        return "redirect:/cities";
    }
}
