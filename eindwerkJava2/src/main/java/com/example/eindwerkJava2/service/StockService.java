package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Location;
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

    public Optional<Stock> findById(Long id){
        return stockRepository.findById(id);
    }

    public Stock findStockById(Long Stockid){
        return this.stockRepository.findByStockId(Stockid);
    }

    public List<Stock> findStocksByLocation(Location location){
        return this.stockRepository.findByLocation(location.getLocationId());
    }

    public int countArticlesOnLocation(long locationId){
        return this.stockRepository.countArticlesPerLocation(locationId);
    }

    public Optional<Stock> findStockByArticleIdAndLocationId(Long articleId, Long locationId){
        return stockRepository.findStockByArticleIdAndLocationId(articleId,locationId);
    }

    public List<Stock> findStockByArticleId(Article article ){return this.stockRepository.findByArticle(article.getArticleId());};

    public void deleteStock(Stock stock)
    {
        stock.setActiveStock(0);
        this.stockRepository.save(stock);
    }

    public List<Stock> activeStock()
    {
        return this.stockRepository.activeStock();
    }

    public boolean isStockLocationEmpty(Location location){
        List<Stock> stocksOnLocation = findStocksByLocation(location);
        double amountOnLocation = 0.0;
        for(Stock stock:stocksOnLocation){
            if(stock.getAmount()!=0){
                amountOnLocation=amountOnLocation+stock.getAmount();
            }
        }
        return amountOnLocation == 0.0;
    }


}
