package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.service.MutationService;
import com.example.eindwerkJava2.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

public class MutationController {
    private final MutationService mutationService;


    @Autowired
    public MutationController(MutationService mutationService) {
        this.mutationService = mutationService;
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
     return "form_mutation";
    }

    @PostMapping("/saveMutation")
    public String saveMutation(@ModelAttribute("mutation") Mutation mutation){
        this.mutationService.addMutation(mutation);
        return "redirect:/mutation";
    }

}
