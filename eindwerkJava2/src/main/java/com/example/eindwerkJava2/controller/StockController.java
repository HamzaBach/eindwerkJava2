package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.LocationService;
import com.example.eindwerkJava2.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/stock")
    public String viewStock(Model model){
        model.addAttribute("stockList",stockService.activeStock());
        return "stock";
    }

    @GetMapping("/new/stock")
    public String showNewStockForm(Model model){

        Stock stock = new Stock();
        model.addAttribute("stock", stock);
        model.addAttribute("locationList", locationService.getAllLocations());
        model.addAttribute("articleList", articleService.getActiveArticles().getEntities());

        return "/forms/form_stock";
    }

    @PostMapping("/saveStock")
    public String saveStock(@ModelAttribute("Stock")Stock stock)
    {
        this.stockService.saveStock(stock);
        return "redirect:/stock";
    }

    @GetMapping("/edit/stock/{stockId}")
    public String viewUpdateStockForm(@PathVariable("stockId")Long stockId, Model model)
    {
        Stock stock = stockService.findStockById(stockId);
        model.addAttribute("stock", stock);
        model.addAttribute("locationList", locationService.getAllLocations());
        model.addAttribute("articleList", articleService.getActiveArticles().getEntities());
        return "/forms/form_stock";
    }

    @GetMapping("/delete/stock/{stockId}")
    public String deleteStock(@PathVariable("stockId")Long stockId, Model model)
    {
        Stock stock = stockService.findStockById(stockId);
        this.stockService.deleteStock(stock);
        return "redirect:/stock";
    }






}
