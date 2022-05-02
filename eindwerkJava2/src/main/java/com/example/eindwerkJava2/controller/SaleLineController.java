package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.CreateSaleLineDto;
import com.example.eindwerkJava2.model.SaleLine;
import com.example.eindwerkJava2.service.SaleLineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sale-lines")
public class SaleLineController {

    private final SaleLineService saleLineService;

    public SaleLineController(SaleLineService saleLineService) {
        this.saleLineService = saleLineService;
    }

    @PostMapping
    public void createSaleLine(@RequestBody CreateSaleLineDto createSaleLineDto){
        saleLineService.saveSaveLine(createSaleLineDto);
    }

    @GetMapping("{saleHeaderId}")
    public List<SaleLine> getSaleLinesFromHeader(@PathVariable Long saleHeaderId){
        return saleLineService.getAllSaleLinesFromHeader(saleHeaderId);
    }

}
