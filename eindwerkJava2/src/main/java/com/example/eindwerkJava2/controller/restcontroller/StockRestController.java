package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Stock;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.StockService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 /*   @GetMapping(path="stockinfo/{barcode}")
    private String stockOfArticle(@PathVariable String barcode) throws JSONException {
        Article article = articleService.findArticleByBarcode(barcode);
        Stock stock = stockService.findStockByArticleId(article);

        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("name", stock.getArticle().getArticleName());
        jo.put("articleCode", stock.getArticle().getArticleBarcode());
        jo.put("location", stock.getLocation().getLocationName());
        jo.put("amount", stock.getAmount());
        ja.put(jo);
        JSONObject json = new JSONObject();
        json.put("article", ja);
        return json.toString();
    }*/


}
