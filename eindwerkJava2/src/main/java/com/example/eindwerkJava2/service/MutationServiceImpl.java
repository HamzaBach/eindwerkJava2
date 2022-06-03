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
    private final LocationService locationService;


    @Autowired
    public MutationServiceImpl(MutationRepository mutationRepository, StockService stockService, TransactionRepository transactionRepository, LocationService locationService) {
        this.mutationRepository = mutationRepository;
        this.stockService = stockService;
        this.transactionRepository = transactionRepository;
        this.locationService = locationService;
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
        boolean existsStockByLocation = stockService.existsStockByLocation(mutation.getLocation());
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
                    if(!updateStockArticle.getLocation().getLocationType().isSingleStorage()||amountOfArticlesOnLocation<=1){
                        updatedStockTotalAmount = updateStockArticle.getAmount() + mutation.getAmount();
                        updateStockArticle.setAmount(updatedStockTotalAmount);
                        stockService.saveStock(updateStockArticle);
                        mutation.setAmount(mutation.getAmount()*mutation.getTransactionType().getTransactionTypeFactor());
                        mutationRepository.save(mutation);
                        isStockPresentOnLocation += 1;
                    } else {
                        isAddStockSuccessfull.setIsSuccessfull(false);
                        isAddStockSuccessfull.setMessage("Cannot add a different article to a single storage location!");
                        return isAddStockSuccessfull;
                    }
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
        stockMovementValidator(mutation, isAddStockSuccessfull, updatedStockTotalAmount);
        return isAddStockSuccessfull;

    }

    public SuccessEvaluator<Mutation> removeStock(Mutation mutation) {
        SuccessEvaluator<Mutation> isRemoveStockSuccessfull = new SuccessEvaluator<>();
        double updatedStockTotalAmount = 0.0;
        List<Stock> stocksOnLocation = stockService.findStocksByLocation(mutation.getLocation());
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
                    mutation.setAmount(mutation.getAmount()*mutation.getTransactionType().getTransactionTypeFactor());
                    mutationRepository.save(mutation);


                }
            }
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


    public SuccessEvaluator<Mutation> moveStock(Mutation mutation, long targetLocationID) {
        SuccessEvaluator<Mutation> isMoveStockSuccessful = new SuccessEvaluator<>();
        SuccessEvaluator<Mutation> isRemoveStockSuccessful = removeStock(mutation);
        if (isRemoveStockSuccessful.getIsSuccessfull()) {
            Mutation copiedMutation = createCopyOfMutation(mutation);
            copiedMutation.setAmount(Math.abs(copiedMutation.getAmount()));
            copiedMutation.setLocation(locationService.findByLocationId(targetLocationID));
            SuccessEvaluator<Mutation> isAddStockSuccessful = addStock(copiedMutation);
            if (!isAddStockSuccessful.getIsSuccessfull()) {
                isMoveStockSuccessful.setMessage(isAddStockSuccessful.getMessage());
            }
        } else {
            isMoveStockSuccessful.setIsSuccessfull(false);
            isMoveStockSuccessful.setMessage(isRemoveStockSuccessful.getMessage());
        }
        return isMoveStockSuccessful;
    }

    private Mutation createCopyOfMutation(Mutation mutation){
        Mutation copiedMutation = new Mutation();
        copiedMutation.setAmount(mutation.getAmount());
        copiedMutation.setTransactionType(mutation.getTransactionType());
        copiedMutation.setLocation(mutation.getLocation());
        copiedMutation.setArticle(mutation.getArticle());
        copiedMutation.setUser(mutation.getUser());
        copiedMutation.setComment(mutation.getComment());
        copiedMutation.setLocalDateTime(mutation.getLocalDateTime());
        return copiedMutation;
    }

    public SuccessEvaluator<Mutation> correctStockAmount(Mutation mutation) {
        SuccessEvaluator<Mutation> isCorrectionSuccessful = new SuccessEvaluator<>();
        double updatedStockTotalAmount = 0.0;
        int isStockPresentOnLocation = 0;
        List<Stock> stocksOnLocation = stockService.findStocksByLocation(mutation.getLocation());
        for (Stock stock : stocksOnLocation) {
            if (stock.getArticle() == mutation.getArticle()) {
                Double amountDifference = mutation.getAmount() - stock.getAmount();
                if (amountDifference > 0) {
                    mutation.setAmount(amountDifference);
                    mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie opboeken").get());
                    updatedStockTotalAmount=stock.getAmount() + amountDifference;
                    stock.setAmount(updatedStockTotalAmount);
                    mutation.setAmount(mutation.getAmount()*mutation.getTransactionType().getTransactionTypeFactor());
                    mutationRepository.save(mutation);
                    stockService.saveStock(stock);
                } else if (amountDifference < 0) {
                    mutation.setAmount(amountDifference);
                    mutation.setTransactionType(transactionRepository.findByTransactionTypeName("Correctie afboeken").get());
                    updatedStockTotalAmount=stock.getAmount() + amountDifference;
                    stock.setAmount(updatedStockTotalAmount);
                    mutation.setAmount(mutation.getAmount()*mutation.getTransactionType().getTransactionTypeFactor());
                    mutationRepository.save(mutation);
                    stockService.saveStock(stock);
                }
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
    public List<Mutation> findByArticle(Article article){
        return mutationRepository.findByArticle(article);
    }

}
