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
        this.transactionRepository = transactionRepository;
    }

    public SuccessEvaluator<Mutation> getMutations() {
        SuccessEvaluator<Mutation> getMutationsSuccess = new SuccessEvaluator<>();
        getMutationsSuccess.setEntities(mutationRepository.findAll());
        if (getMutationsSuccess.getEntities().size() == 0) {
            getMutationsSuccess.setIsSuccessfull(false);
            getMutationsSuccess.setMessage("Empty mutations database.");
        } else {
            getMutationsSuccess.setIsSuccessfull(true);
        }
        return getMutationsSuccess;
    }


    public SuccessEvaluator<Mutation> addStock(Mutation mutation) {
        SuccessEvaluator<Mutation> isAddStockSuccessfull = new SuccessEvaluator<>();
        double updatedStockTotalAmount = 0.0;
        //Stock stockTo = stockService.findStockByLocation(mutation.getLocation());
        mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Opboeken").get());
        boolean existsStockByLocation = stockService.existsStockByLocation(mutation.getLocation());
        if (!existsStockByLocation) {
            //Empty location use case
            Stock initstock = new Stock();
            initstock.reserveStockLocation(mutation);
            stockService.saveStock(initstock);
            Stock newStockOnLocation = new Stock(mutation.getArticle(), mutation.getAmount(), mutation.getLocation(), 1);
            stockService.saveStock(newStockOnLocation);
            mutationRepository.save(mutation);
            updatedStockTotalAmount = mutation.getAmount();
        } else {
            int isStockPresentOnLocation = 0;
            List<Stock> stocksOnLocation = stockService.findStockByLocation(mutation.getLocation());
            for (Stock updateStockArticle : stocksOnLocation) {
                if (updateStockArticle.getArticle() == mutation.getArticle()) {
                    // multiple articles on same location use case -> update article if it is the same as the one from mutations
                    updatedStockTotalAmount = updateStockArticle.getAmount() + mutation.getAmount();
                    updateStockArticle.setAmount(updatedStockTotalAmount);
                    stockService.saveStock(updateStockArticle);
                    mutationRepository.save(mutation);
                    isStockPresentOnLocation += 1;
                }
            }
            if (isStockPresentOnLocation == 0) {
                // mutation.getarticle is not present on location use case
                Stock newArticleOnSameLocation = new Stock(mutation.getArticle(), mutation.getAmount(), mutation.getLocation(), 1);
                stockService.saveStock(newArticleOnSameLocation);
                mutationRepository.save(mutation);
                updatedStockTotalAmount = mutation.getAmount();
            }
        }
        //Retrieve saved article on location and compare the amount in order to determine whether the save was successful.
        Optional<Stock> recentlyUpdatedStock = stockService.findStockByArticleIdAndLocationId(mutation.getArticle().getArticleId(), mutation.getLocation().getLocationId());
        if (recentlyUpdatedStock.get().getAmount() == updatedStockTotalAmount) {
            isAddStockSuccessfull.setIsSuccessfull(true);
        } else {
            isAddStockSuccessfull.setIsSuccessfull(false);
            isAddStockSuccessfull.setMessage("Mismatch in stock amount expected amount =" + updatedStockTotalAmount + ", retrieved amount from db = " + mutation.getAmount() +
                    " on location: " + mutation.getLocation());
        }
        return isAddStockSuccessfull;

    }

    public SuccessEvaluator<Mutation> removeStock(Mutation mutation) {
        SuccessEvaluator<Mutation> isRemoveStockSuccessfull = new SuccessEvaluator<>();
        double updatedStockTotalAmount = 0.0;
        List<Stock> stocksOnLocation = stockService.findStockByLocation(mutation.getLocation());
        mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Afboeken").get());
        for (Stock updateStockArticle : stocksOnLocation) {
            if (updateStockArticle.getArticle() == mutation.getArticle()) {
                // multiple articles on same location use case -> update article if it is the same as the one from mutations
                updatedStockTotalAmount = updateStockArticle.getAmount() - mutation.getAmount();

                if (updatedStockTotalAmount < 0) {
                    isRemoveStockSuccessfull.setIsSuccessfull(false);
                    isRemoveStockSuccessfull.setMessage("Insufficient amount (" + updateStockArticle.getAmount() + ") while the to be retrieved amount is higher: " + mutation.getAmount() +
                            " on location " + mutation.getLocation());
                } else {
                    updateStockArticle.setAmount(updatedStockTotalAmount);
                    stockService.saveStock(updateStockArticle);
                    mutationRepository.save(mutation);
                }


            }
        }
        Optional<Stock> recentlyUpdatedStock = stockService.findStockByArticleIdAndLocationId(mutation.getArticle().getArticleId(), mutation.getLocation().getLocationId());
        if (recentlyUpdatedStock.get().getAmount() == updatedStockTotalAmount) {
            isRemoveStockSuccessfull.setIsSuccessfull(true);
        } else {
            isRemoveStockSuccessfull.setIsSuccessfull(false);
            isRemoveStockSuccessfull.setMessage("Mismatch in stock amount expected amount =" + updatedStockTotalAmount + ", retrieved amount from db = " + mutation.getAmount() +
                    " on location: " + mutation.getLocation());
        }

        return isRemoveStockSuccessfull;
    }


    public SuccessEvaluator<Mutation> moveStock(Mutation mutation) {
        SuccessEvaluator<Mutation> isMoveStockSuccessful = new SuccessEvaluator<>();
        SuccessEvaluator<Mutation> isRemoveStockSuccessful = removeStock(mutation);
        if (isRemoveStockSuccessful.getIsSuccessfull()) {
            SuccessEvaluator<Mutation> isAddStockSuccessful = addStock(mutation);
            if (!isAddStockSuccessful.getIsSuccessfull()) {
                isMoveStockSuccessful.setMessage(isAddStockSuccessful.getMessage());
            }
        } else {
            isMoveStockSuccessful.setIsSuccessfull(false);
            isMoveStockSuccessful.setMessage(isRemoveStockSuccessful.getMessage());
        }
        return isMoveStockSuccessful;
    }

    public SuccessEvaluator<Mutation> correctStockAmount(Mutation mutation) {
        SuccessEvaluator<Mutation> isCorrectionSuccessful = new SuccessEvaluator<>();
        double updatedStockTotalAmount = 0.0;
        int isStockPresentOnLocation = 0;
        List<Stock> stocksOnLocation = stockService.findStockByLocation(mutation.getLocation());
        for (Stock stock : stocksOnLocation) {
            if (stock.getArticle() == mutation.getArticle()) {
                Double amountDifference = mutation.getAmount() - stock.getAmount();
                if (amountDifference > 0) {
                    mutation.setAmount(amountDifference);
                    mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie opboeken").get());
                    updatedStockTotalAmount=stock.getAmount() + amountDifference;
                    stock.setAmount(updatedStockTotalAmount);
                    mutationRepository.save(mutation);
                    stockService.saveStock(stock);
                } else if (amountDifference < 0) {
                    mutation.setAmount(amountDifference);
                    mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie afboeken").get());
                    updatedStockTotalAmount=stock.getAmount() + amountDifference;
                    stock.setAmount(updatedStockTotalAmount);
                    mutationRepository.save(mutation);
                    stockService.saveStock(stock);
                }
            }
        }
        //Retrieve saved article on location and compare the amount in order to determine whether the save was successful.
        Optional<Stock> recentlyUpdatedStock = stockService.findStockByArticleIdAndLocationId(mutation.getArticle().getArticleId(), mutation.getLocation().getLocationId());
        if (recentlyUpdatedStock.get().getAmount()==updatedStockTotalAmount) {
            isCorrectionSuccessful.setIsSuccessfull(true);
        } else {
            isCorrectionSuccessful.setIsSuccessfull(false);
            isCorrectionSuccessful.setMessage("Correction on the stock amount was not correctly done!");
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
