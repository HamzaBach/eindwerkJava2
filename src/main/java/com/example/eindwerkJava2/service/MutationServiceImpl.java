package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.Exceptions.NegativeInventoryException;
import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.repositories.MutationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MutationServiceImpl implements MutationService {

    @Autowired
    private final MutationRepository mutationRepository;
    @Autowired
    private final StockService stockService;



    @Autowired
    public MutationServiceImpl(MutationRepository mutationRepository, StockService stockService) {
        this.mutationRepository = mutationRepository;
        this.stockService = stockService;
    }

    public List<Mutation> getMutations() {
        return mutationRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = NegativeInventoryException.class)
    public void addMutation(Mutation mutation) throws NegativeInventoryException {
        mutationRepository.save(mutation);
        Stock stock = stockService.findStockByArticleId(mutation.getArticle());

        if (mutation.getTransactionType().getTransactionTypeFactor() == -1D && stock.getAmount() - mutation.getAmount() < 0){
            throw new NegativeInventoryException("De stock mag niet negatief zijn");
        }
        stock.setAmount(updateArticleAmount(mutation));
        stockService.saveStock(stock);
    }

    public Optional<Mutation> findById(Long id) {
        return mutationRepository.findById(id);
    }

    public void deleteMutation(Mutation mutation) {
        this.mutationRepository.delete(mutation);
    }

    public Double updateArticleAmount(Mutation mutation) {
        Double totalamount =0.0;
        List<Mutation> mutationList = mutationRepository.findByArticle(mutation.getArticle());

        for (Mutation mutation1 : mutationList) {
            totalamount+= mutation1.getAmount() * mutation1.getTransactionType().getTransactionTypeFactor();
        }

        return totalamount;
    }

}
