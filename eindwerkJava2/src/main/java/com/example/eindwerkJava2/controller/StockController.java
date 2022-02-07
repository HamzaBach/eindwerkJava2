package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stock")
    public String viewStock(Model model){
        model.addAttribute("stockList",stockService.getStock());
        return "stock";
    }

    @GetMapping("/newStockForm")
    public String showNewStockForm(Model model){

        Stock stock = new Stock();
        model.addAttribute("stock", stock);
        return "newStockForm";


    }

    @PostMapping("/saveStock")
    public String saveStock(@ModelAttribute("Stock")Stock stock)
    {
        this.stockService.saveStock(stock);
        return "redirect:/stock";
    }




}
