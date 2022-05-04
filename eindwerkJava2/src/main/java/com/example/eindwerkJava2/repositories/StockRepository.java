package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByStockId(Long stockId);

    @Query("SELECT u FROM Stock u WHERE u.activeStock = 1")
    List<Stock>activeStock();
    //TODO Waarschijnlijk moet dit een list worden omdat meerdere artikels op verschillende locaties kunnen zijn
    Stock findByArticle(Article article);
    Stock findByLocation(Location location);
}
