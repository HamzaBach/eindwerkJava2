package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByStockId(Long stockId);

    @Query("SELECT u FROM Stock u WHERE u.activeStock = 1")
    List<Stock>activeStock();
    @Query(value="SELECT * FROM Stock WHERE article_id = ?1 AND active_stock = 1",nativeQuery = true)
    List<Stock> findByArticle(long articleId);
    @Query(value="SELECT * FROM Stock WHERE location_id = ?1 AND active_stock = 1",nativeQuery = true)
    List<Stock> findByLocation(long locationId);
    @Query(value="SELECT * FROM Stock WHERE article_id = ?1 AND location_id = ?2 AND active_stock = 1",nativeQuery = true)
    Optional<Stock> findStockByArticleIdAndLocationId(long articleId, long locationId);
    @Query(value = "SELECT COUNT(*) FROM Stock WHERE location_id=?1 AND active_stock = 1", nativeQuery = true)
    int countArticlesPerLocation(long locationId);
}
