package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
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
        mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Opboeken").get());
        double transactionTypeFactor = mutation.getTransactionType().getTransactionTypeFactor();
        boolean existsStockByLocation = stockService.existsStockByLocation(mutation.getLocation());
        boolean isSingleStorage = mutation.getLocation().getLocationType().isSingleStorage();
        if (!existsStockByLocation) {
            //Empty location use case
            Stock newStockOnLocation = new Stock(mutation.getArticle(), mutation.getAmount(), mutation.getLocation(), 1);
            stockService.saveStock(newStockOnLocation);
            mutationRepository.save(mutation);
            updatedStockTotalAmount = mutation.getAmount();
        } else {
            int isStockPresentOnLocation = 0;
            List<Stock> stocksOnLocation = stockService.findStocksByLocation(mutation.getLocation());
            int amountOfArticlesOnLocation = stocksOnLocation.size();

            for (Stock updateStockArticle : stocksOnLocation) {
                if (updateStockArticle.getArticle() == mutation.getArticle()) {
                    // multiple articles on same location use case OR singleStorage with only 1 article on location -> update article if it is the same as the one from mutations
                    isStockPresentOnLocation += 1;
                    if (!isSingleStorage || amountOfArticlesOnLocation == 1) {
                        updatedStockTotalAmount = updateStockArticle.getAmount() + transactionTypeFactor * mutation.getAmount();
                        updateStockArticle.setAmount(updatedStockTotalAmount);
                        stockService.saveStock(updateStockArticle);
                        mutationRepository.save(mutation);
                    } else {
                        isAddStockSuccessfull.setIsSuccessfull(false);
                        isAddStockSuccessfull.setMessage("Cannot add a different article to a single storage location!");
                        return isAddStockSuccessfull;
                    }
                }
            }
            if (isStockPresentOnLocation == 0 && !isSingleStorage) {
                // mutation.getarticle is not present on location use case
                Stock newArticleOnSameLocation = new Stock(mutation.getArticle(), mutation.getAmount(), mutation.getLocation(), 1);
                stockService.saveStock(newArticleOnSameLocation);
                mutationRepository.save(mutation);
                updatedStockTotalAmount = mutation.getAmount();
            }
        }
        //Retrieve saved article on location and compare the amount in order to determine whether the save was successful.
        stockMovementValidator(mutation, isAddStockSuccessfull, updatedStockTotalAmount);
        return isAddStockSuccessfull;

    }

    public SuccessEvaluator<Mutation> removeStock(Mutation mutation) {
        SuccessEvaluator<Mutation> isRemoveStockSuccessfull = new SuccessEvaluator<>();
        double updatedStockTotalAmount = 0.0;
        List<Stock> stocksOnLocation = stockService.findStocksByLocation(mutation.getLocation());
        mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Afboeken").get());
        double transactionTypeFactor = mutation.getTransactionType().getTransactionTypeFactor();
        int isStockRemoved = 0;
        for (Stock updateStockArticle : stocksOnLocation) {
            if (updateStockArticle.getArticle() == mutation.getArticle()) {
                // multiple articles on same location use case -> update article if it is the same as the one from mutations
                updatedStockTotalAmount = updateStockArticle.getAmount() + transactionTypeFactor * mutation.getAmount();

                if (updatedStockTotalAmount < 0) {
                    isRemoveStockSuccessfull.setIsSuccessfull(false);
                    isRemoveStockSuccessfull.setMessage("Insufficient amount (" + updateStockArticle.getAmount() + ") while the to be retrieved amount is higher: " + mutation.getAmount() +
                            " on location " + mutation.getLocation());
                } else {
                    updateStockArticle.setAmount(updatedStockTotalAmount);
                    stockService.saveStock(updateStockArticle);
                    mutationRepository.save(mutation);
                    isStockRemoved += 1;
                }
            }
        }
        if (isStockRemoved == 0) {
            isRemoveStockSuccessfull.setIsSuccessfull(false);
            isRemoveStockSuccessfull.setMessage("Unable to reduce the stock amount of " + mutation.getArticle().getArticleName() + " as it is not present on the given location " + mutation.getLocation());
        }
        stockMovementValidator(mutation, isRemoveStockSuccessfull, updatedStockTotalAmount);
        return isRemoveStockSuccessfull;
    }

    private void stockMovementValidator(Mutation mutation, SuccessEvaluator<Mutation> successObject, double updatedStockTotalAmount) {
        Optional<Stock> recentlyUpdatedStock = stockService.findStockByArticleIdAndLocationId(mutation.getArticle().getArticleId(), mutation.getLocation().getLocationId());
        if (recentlyUpdatedStock.get().getAmount() == updatedStockTotalAmount) {
            successObject.setIsSuccessfull(true);
        } else {
            successObject.setIsSuccessfull(false);
            successObject.setMessage("Mismatch in stock amount expected amount =" + updatedStockTotalAmount + ", retrieved amount from db = " + mutation.getAmount() +
                    " on location: " + mutation.getLocation());
        }
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
        double updatedStockTotalAmount = mutation.getAmount();
        List<Stock> stocksOnLocation = stockService.findStocksByLocation(mutation.getLocation());
        for (Stock stock : stocksOnLocation) {
            if (stock.getArticle() == mutation.getArticle() && stock.getAmount() != mutation.getAmount()) {
                double amountDifference = mutation.getAmount() - stock.getAmount();
                if (amountDifference > 0) {
                    mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie opboeken").get());
                } else if (amountDifference < 0) {
                    mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie afboeken").get());
                }
                mutation.setAmount(amountDifference);
                stock.setAmount(updatedStockTotalAmount);
                mutationRepository.save(mutation);
                stockService.saveStock(stock);
                isCorrectionSuccessful.setMessage("A stock correction on stockId " + stock.getStockId() + " was successfully performed " +
                        "(initial amount: " + stock.getAmount() + ", current amount: " + updatedStockTotalAmount + ")");
            }
        }
        //Retrieve saved article on location and compare the amount in order to determine whether the save was successful.
        stockMovementValidator(mutation, isCorrectionSuccessful, updatedStockTotalAmount);
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

    @Override
    public List<Mutation> findByArticle(Article article) {
        return mutationRepository.findByArticle(article);
    }

}
