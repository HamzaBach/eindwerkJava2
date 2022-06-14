package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Mutation;
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

    public ChartService (MutationServiceImpl mutationService, StockService stockService, TransactionService transactionService){
        this.mutationService=mutationService;
        this.stockService=stockService;
        this.transactionService=transactionService;
    }

    public Map<LocalDateTime, Double> getSalesChartFromDateToNow(LocalDateTime startDate){
        List<Mutation> mutationList= mutationService.getMutationsWithStartingDateAndTransactionType(startDate,transactionService.getOutboundTransactionType());
        Map<LocalDateTime,Double> chartData = new TreeMap<>();
        for(Mutation mutation:mutationList){
            chartData.put(mutation.getLocalDateTime(),Math.abs(mutation.getAmount()));
        }
        return chartData;
    }
}
