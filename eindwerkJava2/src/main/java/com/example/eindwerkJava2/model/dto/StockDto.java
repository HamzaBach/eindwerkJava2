package com.example.eindwerkJava2.model.dto;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StockDto extends Stock {

    private Location locationto;
    private String comment;

    public void convertStocktoDto(Stock stock){
       setLocation(stock.getLocation());
       setAmount(stock.getAmount());
       setStockId(stock.getStockId());
       setArticle(stock.getArticle());
       setActiveStock(stock.getActiveStock());
    }
}
