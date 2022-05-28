package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.model.dto.CreateSaleLineDto;
import com.example.eindwerkJava2.model.dto.SaleLineDto;
import com.example.eindwerkJava2.repositories.SaleLineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleLineService {
    private final SaleLineRepository saleLineRepository;
    private final ArticleService articleService;
    private final SaleHeaderService saleHeaderService;
    private final MutationService mutationService;
    private final TransactionService transactionService;
    private final LocationService locationService;
    private final UserService userService;
    private final StockService stockService;


    public SaleLineService(SaleLineRepository saleLineRepository, ArticleService articleService, SaleHeaderService saleHeaderService, MutationService mutationService, TransactionService transactionService, LocationService locationService, UserService userService, StockService stockService) {
        this.saleLineRepository = saleLineRepository;
        this.articleService = articleService;
        this.saleHeaderService = saleHeaderService;
        this.mutationService = mutationService;
        this.transactionService = transactionService;
        this.locationService = locationService;
        this.userService = userService;
        this.stockService = stockService;
    }

    public void saveSaveLine(CreateSaleLineDto createSaleLineDto) {
        SaleLine saleLine = new SaleLine();
        Article article = (Article) articleService.findByBarcode(createSaleLineDto.getBarcode()).getEntity();
        saleLine.setArticle(article);
        saleLine.setQuantity(createSaleLineDto.getQuantity());
        saleLine.setUnitPrice(createSaleLineDto.getUnitPrice());
        SaleHeader saleHeader = saleHeaderService.getSaleHeaderById(createSaleLineDto.getSaleHeaderId());
        saleLine.setSaleHeader(saleHeader);
        saleHeader.setTotalPrice(saleHeader.getTotalPrice() + createSaleLineDto.getUnitPrice() * createSaleLineDto.getQuantity());
        saleHeaderService.createHeader(saleHeader);
        saleLineRepository.save(saleLine);

        List<Stock> stocks = stockService.findStockByArticleId(saleLine.getArticle());
        Location locationInStore = null;
        for (Stock stock: stocks
             ) {
           if(stock.getLocation().getWarehouse().isStore()){
               locationInStore = stock.getLocation();
           }
        }

        Mutation soldItems = new Mutation(
                articleService.findByBarcode(createSaleLineDto.getBarcode()).getEntity(),  //artikel
                (double) createSaleLineDto.getQuantity(),                                 //aantal
                "Verkocht item",                                                 //comment
                locationInStore,                                                         //locatie
                transactionService.getSaleType(),                                       //transactiontype
                userService.getUserByUserName(saleHeader.getNameSalesPerson()),
                LocalDateTime.now());

        mutationService.removeStock(soldItems);

    }

    public List<SaleLine> getAllSaleLinesFromHeader(Long saleHeaderId) {
        List<SaleLine> saleLines = saleLineRepository.findAll();
        return saleLines
                .stream()
                .filter(saleLine -> saleLine.getSaleHeader().equals(saleHeaderService.getSaleHeaderById(saleHeaderId)))
                .collect(Collectors.toList());
    }

    public SaleLineDto toSaleLineDto(SaleLine saleLine){
        return  new SaleLineDto(
                saleLine.getArticle().getArticleName(),
                saleLine.getQuantity(),
                saleLine.getUnitPrice());
    }

    public List<SaleLineDto> saleLineDtos(List<SaleLine> saleLines){
        return saleLines.stream()
                .map(this::toSaleLineDto)
                .collect(Collectors.toList());
    }
}
