package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.Exceptions.NegativeInventoryException;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class MutationController {
    @Autowired
    private final MutationServiceImpl  mutationServiceImp;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ArticleService articleService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LocationService locationService;

    public MutationController(MutationServiceImpl mutationServiceImp, UserService userservice, ArticleService articleService, TransactionService transactionService, LocationService locationService) {
        this.mutationServiceImp = mutationServiceImp;
        this.userService =userservice;
        this.articleService=articleService;
        this.transactionService = transactionService;
        this.locationService = locationService;
    }

    @GetMapping("/mutation")
    public String  viewMutations(Model model){
        Mutation mutation = new Mutation();

        model.addAttribute("mutationList", mutationServiceImp.getMutations());

        return "mutation";
    }

    @GetMapping("/new/mutation")
    public String showNewMutationForm(Model model){
        Mutation mutation = new Mutation();

        model.addAttribute("mutation",new Mutation());
        model.addAttribute("transactiontypeList", transactionService.getTransactiontypes());
        model.addAttribute("userList",userService.getActiveUsers().getEntities());
        model.addAttribute("articleList", articleService.getActiveArticles().getEntities());
        model.addAttribute("locationList", locationService.getAllLocations());
        return "/forms/form_mutation";
    }

    @PostMapping("/saveMutation")
    public String saveMutation(@ModelAttribute("mutation") Mutation mutation, Model model) throws NegativeInventoryException {
        this.mutationServiceImp.addStock(mutation);
        return "redirect:/mutation";
    }


    @GetMapping("delete/mutation/{mutationId}")
    public String deleteArticle(@PathVariable("mutationId") Long mutationId){
        Mutation mutation = mutationServiceImp.findById(mutationId).get();
        this.mutationServiceImp.deleteMutation(mutation);
        return "redirect:/mutation";
    }

    @GetMapping("edit/mutation/{mutationId}")
    public String showEditmutationForm(@PathVariable("mutationId") Long mutationId, Model model) {
        Mutation mutation = mutationServiceImp.findById(mutationId).get();
        model.addAttribute("mutation",mutation);
        model.addAttribute("transactiontypeList", transactionService.getTransactiontypes());
        model.addAttribute("userList",userService.getActiveUsers().getEntities());
        model.addAttribute("articleList", articleService.getActiveArticles().getEntities());
        return "/forms/form_mutation";
    }

}
