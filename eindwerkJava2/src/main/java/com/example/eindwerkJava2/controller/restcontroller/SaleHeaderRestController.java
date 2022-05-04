package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.SaleHeader;
import com.example.eindwerkJava2.service.SaleHeaderService;
import com.example.eindwerkJava2.service.SaleLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sale-headers")
public class SaleHeaderRestController {

    private final SaleHeaderService saleHeaderService;
    private final SaleLineService saleLineService;


    public SaleHeaderRestController(SaleHeaderService saleHeaderService, SaleLineService saleLineService) {
        this.saleHeaderService = saleHeaderService;
        this.saleLineService = saleLineService;
    }

    @PostMapping
    public ResponseEntity<String> createHeader(@RequestBody SaleHeader saleHeader){
        saleHeaderService.createHeader(saleHeader);
        return  ResponseEntity.ok(String.valueOf(saleHeader.getSaleHeaderId()));
    }

    @GetMapping
    public List<SaleHeader> getAllHeaders(){
        return saleHeaderService.getAllSaleHeaders();
    }
    @GetMapping("{saleHeaderId}")
    public SaleHeader getHeaderById(@PathVariable Long saleHeaderId){
        return saleHeaderService.getSaleHeaderById(saleHeaderId);
    }
}
