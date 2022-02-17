package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    public List<Stock> getStock(){
        return stockRepository.findAll();
    }

    public void saveStock(Stock stock){
        this.stockRepository.save(stock);
    }

    public Optional<Stock> findById(long id){
        return stockRepository.findById(id);
    }

    public Stock findStockById(Long Stockid){
        return this.stockRepository.findByStockId(Stockid);
    }

    public void deleteStock(Stock stock)
    {
        stock.setActiveStock(0);
        this.stockRepository.save(stock);
    }

    public List<Stock> activeStock()
    {
        return this.stockRepository.activeStock();
    }


}