package com.example.eindwerkJava2.controller;


import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.model.dto.StockDto;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.service.*;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        return "/forms/form_stock_move";
    }

    @PostMapping("/move/Stock")
    public String moveStock(@ModelAttribute("stockDto")StockDto stockdto , @AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirAttrs, Model model)
    {
        User user = userRepository.findByUserNameAndActiveUser(currentUser.getUsername(),1);
        Mutation mutation = new Mutation();
        mutation.setAmount(stockdto.getAmount());
        mutation.setLocation(stockdto.getLocation());
        mutation.setArticle(stockdto.getArticle());
        mutation.setUser(user);
        mutation.setLocalDateTime(LocalDateTime.now());

        SuccessEvaluator<Mutation> isMoveSuccess =this.mutationService.moveStock(mutation,stockdto.getLocationto().getLocationId());
        if(isMoveSuccess.getIsSuccessfull()){
            redirAttrs.addFlashAttribute("success",isMoveSuccess.getMessage());
            return "redirect:/stock";
        } else {
            model.addAttribute("error",isMoveSuccess.getMessage());
            return "/forms/form_stock_move";
        }
    }

    @GetMapping("/delete/stock/{stockId}")
    public String deleteStock(@PathVariable("stockId")Long stockId, Model model)
    {
        Stock stock = stockService.findStockById(stockId);
        this.stockService.deleteStock(stock);
        return "redirect:/stock";
    }

    @GetMapping("/correct/stock/{stockId}")
    public String correctionStockForm(@PathVariable("stockId")Long stockId, Model model)
    {
        Stock stock = stockService.findStockById(stockId);
        StockDto stockdto = new StockDto();
        stockdto.convertStocktoDto(stock);
        model.addAttribute("stockDto", stockdto);
        return "/forms/form_stock_correct";
    }

    @PostMapping("/correct/stock")
    public String correctStock(@ModelAttribute("stockDto")StockDto stockdto , @AuthenticationPrincipal UserDetails currentUser, RedirectAttributes redirAtts, Model model)
    {
        User user = userRepository.findByUserNameAndActiveUser(currentUser.getUsername(),1);
        Mutation mutation = new Mutation();
        mutation.setAmount(stockdto.getAmount());
        mutation.setLocation(stockdto.getLocation());
        mutation.setArticle(stockdto.getArticle());
        mutation.setUser(user);
        mutation.setLocalDateTime(LocalDateTime.now());
        mutation.setComment(stockdto.getComment());

        SuccessEvaluator<Mutation> isCorrectionSuccess = this.mutationService.correctStockAmount(mutation);
        if(isCorrectionSuccess.getIsSuccessfull()){
            redirAtts.addFlashAttribute("success",isCorrectionSuccess.getMessage());
            return "redirect:/stock";
        } else {
            model.addAttribute("error",isCorrectionSuccess.getMessage());
            return "/forms/form_stock_correct";
        }
    }

}
