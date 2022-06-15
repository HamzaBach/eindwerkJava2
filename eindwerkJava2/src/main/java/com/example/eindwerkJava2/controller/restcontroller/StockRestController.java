package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.StockService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class StockRestController {
    private StockService stockService;
    private ArticleService articleService;

    public StockRestController(StockService stockService, ArticleService articleService) {
        this.stockService = stockService;
        this.articleService = articleService;
    }

    //TODO Fix findStockByArticleId which returns List<Stock> and not a single Stock object
   @GetMapping(path="stockinfo/{barcode}")
    private String stockOfArticle(@PathVariable String barcode) throws JSONException {
        Article article = articleService.findArticleByBarcode(barcode);
        List<Stock> stockList = stockService.findStockByArticleId(article);
        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("name", stockList.get(0).getArticle().getArticleName());
        jo.put("articleCode", stockList.get(0).getArticle().getArticleBarcode());
        ja.put(jo);


       JSONArray ja2 = new JSONArray();

       for (Stock stock:stockList) {
           JSONObject jo2 = new JSONObject();
           jo2.put("location", stock.getLocation().getLocationName());
           jo2.put("amount", stock.getAmount());
           ja2.put(jo2);
       }
       JSONObject json = new JSONObject();
       json.put("location", ja2);
        json.put("article", ja);
        return json.toString();
    }


}
