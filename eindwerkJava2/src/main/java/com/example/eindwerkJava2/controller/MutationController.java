package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Category;
import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.MutationService;
import com.example.eindwerkJava2.service.TransactionService;
import com.example.eindwerkJava2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

public class MutationController {
    @Autowired
    private final MutationService mutationService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ArticleService articleService;
    @Autowired
    private TransactionService transactionService;

    public MutationController(MutationService mutationService, UserService userservice, ArticleService articleService) {
        this.mutationService = mutationService;
        this.userService =userservice;
        this.articleService=articleService;
    }

    @GetMapping("/mutation")
    public String  viewMutations(Model model){
        Mutation mutation = new Mutation();

        model.addAttribute("mutationList", mutationService.getMutations());

        return "mutation";
    }

    @GetMapping("/new/mutation")
    public String showNewMutationForm(Model model){
     Mutation mutation = new Mutation();

     model.addAttribute("mutation",new Mutation());
     model.addAttribute("transactiontypeList", transactionService.getTransactiontypes());
     model.addAttribute("userList",userService.getActiveUsers());
     model.addAttribute("articleList", articleService.getActiveArticles());
     return "/forms/form_mutation";
    }

    @PostMapping("/saveMutation")
    public String saveMutation(@ModelAttribute("mutation") Mutation mutation){
        this.mutationService.addMutation(mutation);
        return "redirect:/mutation";
    }

    @GetMapping("delete/mutation/{mutationId}")
    public String deleteArticle(@PathVariable("mutationId") Long mutationId){
        Mutation mutation = mutationService.findById(mutationId).get();
        this.mutationService.deleteMutation(mutation);
        return "redirect:/mutation";
    }

    @GetMapping("edit/mutation/{mutationId}")
    public String showEditmutationForm(@PathVariable("mutationId") Long mutationId, Model model) {
        Mutation mutation = mutationService.findById(mutationId).get();
        model.addAttribute("mutation",mutation);
        model.addAttribute("transactiontypeList", transactionService.getTransactiontypes());
        model.addAttribute("userList",userService.getActiveUsers());
        model.addAttribute("articleList", articleService.getActiveArticles());
        return "/forms/form_mutation";
    }

}
