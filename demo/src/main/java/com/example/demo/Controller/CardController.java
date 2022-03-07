package com.example.demo.Controller;

import com.example.demo.model.Card;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService){this.cardService=cardService;}

    @GetMapping("/card")
    public String viewCard(Model model){
        //List<Card> cards= cardService.getCards(0);
        model.addAttribute("cardList", cardService.getCards());
        return "card2";
    }



    @GetMapping("/showNewCardForm")
    public String showNewCardForm(Model model){
        Card card = new Card();
        model.addAttribute("card", new Card());
        return "form_card";
    }

    @PostMapping("/saveCard")
    public String saveCard(@ModelAttribute("card") Card card){
        this.cardService.addCard(card);
        return "redirect:/card";
    }

    @GetMapping("editCard/{cardId}")
    public String showEditCardForm(@PathVariable("cardId") Long cardId, Model model){
        Card card = cardService.findById(cardId).get();
        model.addAttribute("card", card);
        return "form_card";
    }

    @GetMapping("deleteCard/{cardId}")
    public String deleteCategory(@PathVariable("cardId") Long cardId, Model model){
        Card card = cardService.findById(cardId).get();
        this.cardService.deleteCard(card);
        return "redirect:/card";
    }




}
