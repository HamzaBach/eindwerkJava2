package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.model.dto.StockDto;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class StockController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MutationServiceImpl mutationService;

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

    @GetMapping("/move/stock/{stockId}")
    public String MoveStockForm(@PathVariable("stockId")Long stockId, Model model)
    {
        Stock stock = stockService.findStockById(stockId);
        StockDto stockdto = new StockDto();
        stockdto.convertStocktoDto(stock);
        model.addAttribute("stockDto", stockdto);
        model.addAttribute("locationList", locationService.getAllLocations());
        model.addAttribute("articleList", articleService.getActiveArticles().getEntities());

        return "/forms/form_stock_move";
    }

    @PostMapping("/move/Stock")
    public String moveStock(@ModelAttribute("stockDto")StockDto stockdto , @AuthenticationPrincipal UserDetails currentUser)
    {
        User user = userRepository.findByUserName(currentUser.getUsername());
        Mutation mutation = new Mutation();
        mutation.setAmount(stockdto.getAmount());
        mutation.setLocation(stockdto.getLocation());
        mutation.setArticle(stockdto.getArticle());
        mutation.setUser(user);
        mutation.setLocalDateTime(LocalDateTime.now());


        this.mutationService.moveStock(mutation,stockdto.getLocationto().getLocationId());

        return "redirect:/stock";
    }

    @GetMapping("/delete/stock/{stockId}")
    public String deleteStock(@PathVariable("stockId")Long stockId, Model model)
    {
        Stock stock = stockService.findStockById(stockId);
        this.stockService.deleteStock(stock);
        return "redirect:/stock";
    }







}
