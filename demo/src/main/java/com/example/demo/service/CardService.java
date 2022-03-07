package com.example.demo.service;

import com.example.demo.model.Card;
import com.example.demo.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository){this.cardRepository = cardRepository;}

    public List<Card> getCards(){return cardRepository.findAll();}

    public void addCard(Card card){cardRepository.save(card);}

    public Optional<Card> findById(long id){return cardRepository.findById(id);}

    public void deleteCard(Card card){
        this.cardRepository.delete(card);
    }
}
