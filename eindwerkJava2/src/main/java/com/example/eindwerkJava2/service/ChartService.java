package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Mutation;
import com.example.eindwerkJava2.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ChartService {
    @Autowired
    private final MutationServiceImpl mutationService;
    @Autowired
    private final StockService stockService;
    @Autowired
    private final TransactionService transactionService;

    public ChartService(MutationServiceImpl mutationService, StockService stockService, TransactionService transactionService) {
        this.mutationService = mutationService;
        this.stockService = stockService;
        this.transactionService = transactionService;
    }

    public Map<LocalDateTime, Double> getSalesChartFromDateToNow(LocalDateTime startDate) {
        List<Mutation> mutationList = mutationService.getMutationsWithStartingDateAndTransactionType(startDate, transactionService.getOutboundTransactionType());
        Map<LocalDateTime, Double> chartData = new TreeMap<>();
        for (Mutation mutation : mutationList) {
            Double salesPerDay = Math.abs(mutation.getAmount()) * mutation.getArticle().getArticleSupplier().getSalesPrice();
            chartData.put(mutation.getLocalDateTime(), salesPerDay);
        }
        return chartData;
    }

    public Map<String, Double> getInventoryPerLocation() {
        List<Stock> stockList = stockService.activeStock();
        Map<String, Double> chartData = new TreeMap<>();
        for (Stock stock : stockList) {
            chartData.put(stock.getArticle().getArticleName() + " in " + stock.getLocation().getLocationName(), stock.getAmount());
        }
        return chartData;
    }

    public Map<String, Double> getValuePerLocation() {
        List<Stock> stockList = stockService.activeStock();
        Map<String, Double> chartData = new TreeMap<>();

        for (Stock stock : stockList) {
            Double valueOnLocation = stock.getAmount()*stock.getArticle().getArticleSupplier().getSalesPrice();
            chartData.put(stock.getLocation().getLocationName(), valueOnLocation);
        }
        return chartData;
    }
}
