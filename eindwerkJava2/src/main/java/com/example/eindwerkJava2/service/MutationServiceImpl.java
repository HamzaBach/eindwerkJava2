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
    private final LocationService locationService;
    @Autowired
    private final TransactionService transactionService;


    @Autowired
    public MutationServiceImpl(MutationRepository mutationRepository, StockService stockService, LocationService locationService, TransactionService transactionService) {
        this.mutationRepository = mutationRepository;
        this.stockService = stockService;
        this.locationService = locationService;
        this.transactionService = transactionService;
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
        if(mutation.getTransactionType()!=transactionService.getInternalAdditionTransactionType()){
            mutation.setTransactionType(transactionService.getInboundTransactionType());
        }
        boolean isStockLocationEmpty = stockService.isStockLocationEmpty(mutation.getLocation());
        if (isStockLocationEmpty) {
            //Empty location use case
            Stock newStockOnLocation = new Stock(mutation.getArticle(), mutation.getAmount(), mutation.getLocation(), 1);
            stockService.saveStock(newStockOnLocation);
            mutationRepository.save(mutation);
            updatedStockTotalAmount = mutation.getAmount();
            isAddStockSuccessfull.setIsSuccessfull(true);
            isAddStockSuccessfull.setMessage("Article "+mutation.getArticle().getArticleName()+" has been successfully added to location "+mutation.getLocation().getLocationName()+"" +
                    " with an amount of "+mutation.getAmount()+".");
        } else {
            int isStockPresentOnLocation = 0;
            List<Stock> stocksOnLocation = stockService.findStocksByLocation(mutation.getLocation());
            int amountOfArticlesOnLocation = stocksOnLocation.size();
            for (Stock updateStockArticle : stocksOnLocation) {
                if (updateStockArticle.getArticle() == mutation.getArticle()) {
                    // multiple articles on same location use case OR singleStorage with only 1 article on location -> update article if it is the same as the one from mutations
                    if (!updateStockArticle.getLocation().getLocationType().isSingleStorage() || amountOfArticlesOnLocation <= 1) {
                        updatedStockTotalAmount = updateStockArticle.getAmount() + mutation.getAmount();
                        double initialStockAmount = updateStockArticle.getAmount();
                        double deltaAmount = mutation.getAmount();
                        updateStockArticle.setAmount(updatedStockTotalAmount);
                        stockService.saveStock(updateStockArticle);
                        mutation.setAmount(mutation.getAmount() * mutation.getTransactionType().getTransactionTypeFactor());
                        mutationRepository.save(mutation);
                        isStockPresentOnLocation += 1;
                        isAddStockSuccessfull.setIsSuccessfull(true);
                        isAddStockSuccessfull.setMessage("Article "+mutation.getArticle().getArticleName()+" has been successfully updated on location "+mutation.getLocation().getLocationName()+"" +
                                " from an amount of "+initialStockAmount+" to an amount of "+updateStockArticle.getAmount()+" (delta = "+deltaAmount+").");
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
                isAddStockSuccessfull.setIsSuccessfull(true);
                isAddStockSuccessfull.setMessage("Article "+mutation.getArticle().getArticleName()+" has been successfully added to location "+mutation.getLocation().getLocationName()+"" +
                        " with an amount of "+updatedStockTotalAmount+".");
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
        if (mutation.getTransactionType() != transactionService.getInternalRemovalTransactionType()) {
            mutation.setTransactionType(transactionService.getOutboundTransactionType());//For real outbound (sold items), else it is internal movement across locations.
        }
        for (Stock updateStockArticle : stocksOnLocation) {
            if (updateStockArticle.getArticle().getArticleId() == mutation.getArticle().getArticleId()) {
                // multiple articles on same location use case -> update article if it is the same as the one from mutations
                updatedStockTotalAmount = updateStockArticle.getAmount() - mutation.getAmount();

                if (updatedStockTotalAmount < 0) {
                    isRemoveStockSuccessfull.setIsSuccessfull(false);
                    isRemoveStockSuccessfull.setMessage("Insufficient amount (" + updateStockArticle.getAmount() + ") while the to be retrieved amount is higher: " + mutation.getAmount() +
                            " on location " + mutation.getLocation());
                }
                else {
                    updateStockArticle.setAmount(updatedStockTotalAmount);
                    stockService.saveStock(updateStockArticle);
                    mutation.setAmount(mutation.getAmount() * mutation.getTransactionType().getTransactionTypeFactor());
                    mutationRepository.save(mutation);
                    isRemoveStockSuccessfull.setIsSuccessfull(true);
                    isRemoveStockSuccessfull.setMessage("The article "+mutation.getArticle().getArticleName()+" has been successfully removed from the stock " +
                            "(removed amount = "+mutation.getAmount()+") on location "+mutation.getLocation().getLocationName()+", current stock on that location is "+updatedStockTotalAmount+".");
                    stockMovementValidator(mutation, isRemoveStockSuccessfull, updatedStockTotalAmount);
                    //remove stock if amount on location ==0
                    if(stockService.isStockLocationEmpty(updateStockArticle.getLocation())){
                        stockService.deleteStock(updateStockArticle);
                    }
                }
            }
        }
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
        mutation.setTransactionType(transactionService.getInternalRemovalTransactionType());
        SuccessEvaluator<Mutation> isRemoveStockSuccessful = removeStock(mutation);
        if (isRemoveStockSuccessful.getIsSuccessfull()) {
            isMoveStockSuccessful.setMessage(isRemoveStockSuccessful.getMessage());//Forwarding successMessages from remove action
            Mutation copiedMutation = createCopyOfMutation(mutation);//Copy is needed in order to prevent overwriting the same object in db, a new record is needed!
            copiedMutation.setAmount(Math.abs(copiedMutation.getAmount()));
            copiedMutation.setLocation(locationService.findByLocationId(targetLocationID));
            copiedMutation.setTransactionType(transactionService.getInternalAdditionTransactionType());
            SuccessEvaluator<Mutation> isAddStockSuccessful = addStock(copiedMutation);
            if (!isAddStockSuccessful.getIsSuccessfull()) {
                isMoveStockSuccessful.setMessage(isAddStockSuccessful.getMessage());
            }else{
                isMoveStockSuccessful.setMessage(isAddStockSuccessful.getMessage());//Forwarding successMessages from add action
            }
        } else {
            isMoveStockSuccessful.setIsSuccessfull(false);
            isMoveStockSuccessful.setMessage(isRemoveStockSuccessful.getMessage());
        }
        return isMoveStockSuccessful;
    }

    public Mutation createCopyOfMutation(Mutation mutation) {
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
        List<Stock> stocksOnLocation = stockService.findStocksByLocation(mutation.getLocation());
        for (Stock stock : stocksOnLocation) {
            if (stock.getArticle() == mutation.getArticle()) {
                double amountDifference = mutation.getAmount() - stock.getAmount();
                double initialStockAmount = stock.getAmount();
                if (amountDifference > 0) {
                    mutation.setAmount(amountDifference);
                    mutation.setTransactionType(transactionService.getCorrectionAdditionTransactionType());
                    updatedStockTotalAmount = stock.getAmount() + amountDifference;
                    stock.setAmount(updatedStockTotalAmount);
                    double newStockAmount=stock.getAmount();
                    mutation.setAmount(mutation.getAmount() * mutation.getTransactionType().getTransactionTypeFactor());
                    mutationRepository.save(mutation);
                    stockService.saveStock(stock);
                    isCorrectionSuccessful.setIsSuccessfull(true);
                    isCorrectionSuccessful.setMessage("A stock correction ("+mutation.getArticle().getArticleName()+") on location ("+mutation.getLocation().getLocationName()+") has" +
                            "been successfully increased from "+initialStockAmount+" to "+newStockAmount+".");
                } else if (amountDifference < 0) {
                    mutation.setAmount(Math.abs(amountDifference));
                    mutation.setTransactionType(transactionService.getCorrectionRemovalTransactionType());
                    updatedStockTotalAmount = stock.getAmount() + amountDifference;
                    stock.setAmount(updatedStockTotalAmount);
                    double newStockAmount=stock.getAmount();
                    mutation.setAmount(mutation.getAmount() * mutation.getTransactionType().getTransactionTypeFactor());
                    mutationRepository.save(mutation);
                    stockService.saveStock(stock);
                    isCorrectionSuccessful.setIsSuccessfull(true);
                    isCorrectionSuccessful.setMessage("A stock correction ("+mutation.getArticle().getArticleName()+") on location ("+mutation.getLocation().getLocationName()+") has" +
                            "been successfully decreased from "+initialStockAmount+" to "+newStockAmount+".");
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
    public List<Mutation> findByArticle(Article article) {
        return mutationRepository.findByArticle(article);
    }

}
