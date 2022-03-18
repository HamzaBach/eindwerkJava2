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
    private final MutationService mutationService;
    private final UserService userService;
    private final ArticleService articleService;

    @Autowired
    public MutationController(MutationService mutationService, UserService userservice, ArticleService articleService) {
        this.mutationService = mutationService;
        this.userService =userservice;
        this.articleService=articleService;
    }

    @Autowired
    private TransactionService transactionService;

   public List<Mutation> getMutations() {return mutationService.getMutations();}

    @GetMapping("/mutation")
    public String  viewMutations(Model model){
        Mutation mutation = new Mutation();

        model.addAttribute("mutationList", mutationService.getMutations());

        return "mutation";
    }

    @GetMapping("/showNewMutationForm")
    public String showNewMutationForm(Model model){
     Mutation mutation = new Mutation();

     model.addAttribute("mutation",new Mutation());
     model.addAttribute("transactiontypeList", transactionService.getTransactiontypes());
     model.addAttribute("userList",userService.getActiveUsers());
     model.addAttribute("articleList", articleService.getActiveArticles());
     return "form_mutation";
    }

    @PostMapping("/saveMutation")
    public String saveMutation(@ModelAttribute("mutation") Mutation mutation){
        this.mutationService.addMutation(mutation);
        return "redirect:/mutation";
    }

    @GetMapping("deleteMutation/{mutationId}")
    public String deleteArticle(@PathVariable("mutationId") Long mutationId){
        Mutation mutation = mutationService.findById(mutationId).get();
        this.mutationService.deleteMutation(mutation);
        return "redirect:/mutation";
    }

    @GetMapping("editMutation/{mutationId}")
    public String showEditmutationForm(@PathVariable("mutationId") Long mutationId, Model model) {
        Mutation mutation = mutationService.findById(mutationId).get();
        model.addAttribute("mutation",mutation);
        model.addAttribute("transactiontypeList", transactionService.getTransactiontypes());
        model.addAttribute("userList",userService.getActiveUsers());
        model.addAttribute("articleList", articleService.getActiveArticles());
        return "form_mutation";
    }



}
