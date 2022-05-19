package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.repositories.MutationRepository;
import com.example.eindwerkJava2.repositories.TransactionRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
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
    private final TransactionRepository transactionRepository;


    @Autowired
    public MutationServiceImpl(MutationRepository mutationRepository, StockService stockService, TransactionRepository transactionRepository) {
        this.mutationRepository = mutationRepository;
        this.stockService = stockService;
        this.transactionRepository=transactionRepository;
    }

    public SuccessEvaluator<Mutation> getMutations() {
        SuccessEvaluator<Mutation> getMutationsSuccess = new SuccessEvaluator<>();
        getMutationsSuccess.setEntities(mutationRepository.findAll());
        if (getMutationsSuccess.getEntities().size()==0){
            getMutationsSuccess.setIsSuccessfull(false);
            getMutationsSuccess.setMessage("Empty mutations database.");
        } else {
            getMutationsSuccess.setIsSuccessfull(true);
        }
        return getMutationsSuccess;
    }


    public SuccessEvaluator<Mutation> addStock(Mutation mutation) {
        SuccessEvaluator<Mutation> isAddStockSuccessfull = new SuccessEvaluator<>();
        Stock stockTo = stockService.findStockByLocation(mutation.getLocation());
        mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Opboeken").get());
        if (stockTo == null) {
            Stock initstock = new Stock();
            initstock.setAmount(0d);
            initstock.setLocation(mutation.getLocation());
            initstock.setArticle(mutation.getArticle());
            initstock.setActiveStock(1);
            stockService.saveStock(initstock);
        }
        stockTo = stockService.findStockByLocation(mutation.getLocation());
        double updatedStockTotalAmount = stockTo.getAmount() + mutation.getAmount();
        stockTo.setAmount(updatedStockTotalAmount);
        stockService.saveStock(stockTo);
        mutationRepository.save(mutation);
        if (stockService.findStockByLocation(mutation.getLocation()).getAmount() == updatedStockTotalAmount) {
            isAddStockSuccessfull.setIsSuccessfull(true);
        } else {
            isAddStockSuccessfull.setIsSuccessfull(false);
            isAddStockSuccessfull.setMessage("Mismatch in stock amount expected amount =" + updatedStockTotalAmount + ", retrieved amount from db = " + mutation.getAmount() +
                    " on location: " + mutation.getLocation());
        }
        return isAddStockSuccessfull;

    }

    public SuccessEvaluator<Mutation> removeStock(Mutation mutation) {
        Stock stockFrom = stockService.findStockByLocation(mutation.getLocation());
        double updatedStockTotalAmount = stockFrom.getAmount() - mutation.getAmount();
        stockFrom.setAmount(updatedStockTotalAmount);
        mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Afboeken").get());

        SuccessEvaluator<Mutation> isRemoveStockSuccessfull = new SuccessEvaluator<>();
        if (updatedStockTotalAmount < 0) {
            isRemoveStockSuccessfull.setIsSuccessfull(false);
            isRemoveStockSuccessfull.setMessage("Insufficient amount (" + stockFrom.getAmount() + ") while the to be retrieved amount is higher: " + mutation.getAmount() +
                    " on location " + mutation.getLocation());
        } else {
            stockService.saveStock(stockFrom);
            mutationRepository.save(mutation);
            if (stockService.findStockByLocation(mutation.getLocation()).getAmount() == updatedStockTotalAmount) {
                isRemoveStockSuccessfull.setIsSuccessfull(true);
            } else {
                isRemoveStockSuccessfull.setIsSuccessfull(false);
                isRemoveStockSuccessfull.setMessage("Mismatch in stock amount expected amount =" + updatedStockTotalAmount + ", retrieved amount from db = " + mutation.getAmount() +
                        " on location: " + mutation.getLocation());
            }
        }
        return isRemoveStockSuccessfull;
    }


    public SuccessEvaluator<Mutation> moveStock(Mutation mutation) {
        SuccessEvaluator<Mutation> isMoveStockSuccessful = new SuccessEvaluator<>();
        SuccessEvaluator<Mutation> isRemoveStockSuccessful = removeStock(mutation);
        if (isRemoveStockSuccessful.getIsSuccessfull()) {
            SuccessEvaluator<Mutation> isAddStockSuccessful = addStock(mutation);
            if(!isAddStockSuccessful.getIsSuccessfull()){
                isMoveStockSuccessful.setMessage(isAddStockSuccessful.getMessage());
            }
        } else {
            isMoveStockSuccessful.setIsSuccessfull(false);
            isMoveStockSuccessful.setMessage(isRemoveStockSuccessful.getMessage());
        }
        return isMoveStockSuccessful;
    }

    public SuccessEvaluator<Mutation> correctStockAmount(Mutation mutation){
        Stock stock=stockService.findStockByLocation(mutation.getLocation());
        Double amountDifference = mutation.getAmount()-stock.getAmount();
        SuccessEvaluator<Mutation> isCorrectionSuccessful = new SuccessEvaluator<>();
        if(amountDifference>0){
            mutation.setAmount(amountDifference);
            mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie opboeken").get());
            stock.setAmount(stock.getAmount()+amountDifference);
            mutationRepository.save(mutation);
            stockService.saveStock(stock);
            if(stockService.findStockByLocation(mutation.getLocation()).getAmount()==stock.getAmount()){
                isCorrectionSuccessful.setIsSuccessfull(true);
            }else{
                isCorrectionSuccessful.setIsSuccessfull(false);
                isCorrectionSuccessful.setMessage("Correction on the stock amount was not correctly done!");
            }
        } else if (amountDifference<0){
            mutation.setAmount(amountDifference);
            mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie afboeken").get());
            stock.setAmount(stock.getAmount()+amountDifference);
            mutationRepository.save(mutation);
            stockService.saveStock(stock);
            if(stockService.findStockByLocation(mutation.getLocation()).getAmount()==stock.getAmount()){
                isCorrectionSuccessful.setIsSuccessfull(true);
            }else{
                isCorrectionSuccessful.setIsSuccessfull(false);
                isCorrectionSuccessful.setMessage("Correction on the stock amount was not correctly done!");
            }
        }
        return isCorrectionSuccessful;
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
