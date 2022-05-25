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
    //TODO Waarschijnlijk moet dit een list worden omdat meerdere artikels op verschillende locaties kunnen zijn
    Stock findByArticle(Article article);
    List<Stock> findByLocation(Location location);
    boolean existsStockByLocation(Location location);
    @Query(value="SELECT * FROM Stock WHERE article_id = ?1 AND location_id = ?2 ",nativeQuery = true)
    Optional<Stock> findStockByArticleIdAndLocationId(long articleId, long locationId);
    @Query(value = "SELECT COUNT(*) FROM Stock WHERE location_id=?1", nativeQuery = true)
    int countArticlesPerLocation(long locationId);
}
