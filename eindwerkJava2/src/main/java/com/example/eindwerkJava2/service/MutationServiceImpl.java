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



    public void addStock(Mutation mutation) {
        Stock stockTo = stockService.findStockByLocation(mutation.getLocation());
        if(stockTo == null)
        {
           Stock initstock = new Stock();
            initstock.setAmount(0d);
            initstock.setLocation(mutation.getLocation());
            initstock.setArticle(mutation.getArticle());
            initstock.setActiveStock(1);
            stockService.saveStock(initstock);
        }
        stockTo = stockService.findStockByLocation(mutation.getLocation());
        stockTo.setAmount(stockTo.getAmount() + mutation.getAmount());
        stockService.saveStock(stockTo);
        mutationRepository.save(mutation);
    }

    public String removeStock(Mutation mutation) {
        Stock stockFrom = stockService.findStockByLocation(mutation.getLocation());
        stockFrom.setAmount(stockFrom.getAmount() - mutation.getAmount() );

        if(stockFrom.getAmount() < 0){
            return "Failure";
        } else{
            stockService.saveStock(stockFrom);
            mutationRepository.save(mutation);
            return "Success";
        }


    }


    public void moveStock(Mutation mutation) {

        if( removeStock(mutation) == "Success"){
            addStock(mutation);
        }

    }

    public Optional<Mutation> findById(Long id) {
        return mutationRepository.findById(id);
    }

    public void deleteMutation(Mutation mutation) {
        this.mutationRepository.delete(mutation);
    }

    public Double getArticleAmount(Mutation mutation) {
        Double totalamount = 0.0;
        List<Mutation> mutationList = mutationRepository.findByArticle(mutation.getArticle());

        for (Mutation mutation1 : mutationList) {
            totalamount += mutation1.getAmount() * mutation1.getTransactionType().getTransactionTypeFactor();
        }

        return totalamount;
    }

}
