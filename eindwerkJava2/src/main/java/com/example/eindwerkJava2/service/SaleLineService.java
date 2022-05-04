package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.dto.CreateSaleLineDto;
import com.example.eindwerkJava2.model.SaleHeader;
import com.example.eindwerkJava2.model.SaleLine;
import com.example.eindwerkJava2.model.dto.SaleLineDto;
import com.example.eindwerkJava2.repositories.SaleLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleLineService {
    private final SaleLineRepository saleLineRepository;
    private final ArticleService articleService;
    private final SaleHeaderService saleHeaderService;

    public SaleLineService(SaleLineRepository saleLineRepository, ArticleService articleService, SaleHeaderService saleHeaderService) {
        this.saleLineRepository = saleLineRepository;
        this.articleService = articleService;
        this.saleHeaderService = saleHeaderService;
    }

    public void saveSaveLine(CreateSaleLineDto createSaleLineDto) {
        SaleLine saleLine = new SaleLine();
        Article article = articleService.findByBarcode(createSaleLineDto.getBarcode()).getArticle();
        saleLine.setArticle(article);
        saleLine.setQuantity(createSaleLineDto.getQuantity());
        saleLine.setUnitPrice(createSaleLineDto.getUnitPrice());
        SaleHeader saleHeader = saleHeaderService.getSaleHeaderById(createSaleLineDto.getSaleHeaderId());
        saleLine.setSaleHeader(saleHeader);
        saleHeader.setTotalPrice(saleHeader.getTotalPrice()+createSaleLineDto.getUnitPrice()* createSaleLineDto.getQuantity());
        saleHeaderService.createHeader(saleHeader);
        saleLineRepository.save(saleLine);
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
