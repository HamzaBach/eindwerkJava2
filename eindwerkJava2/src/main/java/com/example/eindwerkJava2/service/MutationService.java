package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.repositories.MutationRepository;
import com.example.eindwerkJava2.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MutationService {

    @Autowired
    private final MutationRepository mutationRepository;
    @Autowired
    private final StockService stockService;



    @Autowired
    public MutationService(MutationRepository mutationRepository,StockService stockService) {
        this.mutationRepository = mutationRepository;
        this.stockService = stockService;
    }

    public List<Mutation> getMutations() {
        return mutationRepository.findAll();
    }

    public void addMutation(Mutation mutation) {
        mutationRepository.save(mutation);
        Stock stock = stockService.findStockByArticleId(mutation.getArticle());
        stock.setAmount(updateArticleAmount(mutation));
        //stockService.saveStock(stock);
    }

    public Optional<Mutation> findById(Long id) {
        return mutationRepository.findById(id);
    }

    public void deleteMutation(Mutation mutation) {
        this.mutationRepository.delete(mutation);
    }

    public Double updateArticleAmount(Mutation mutation) {
        Double totalamount =0.0;
        List<Mutation> mutationList = mutationRepository.findByArticle(mutation);

        for (Mutation mutation1 : mutationList) {
           totalamount+= mutation1.getAmount() * mutation1.getTransactionType().getTransactionTypeFactor();
        }

        return totalamount;
    }

}
