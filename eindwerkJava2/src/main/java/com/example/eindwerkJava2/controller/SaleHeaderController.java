package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.SaleHeader;
import com.example.eindwerkJava2.service.SaleHeaderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sale-headers")
public class SaleHeaderController {

    private final SaleHeaderService saleHeaderService;

    public SaleHeaderController(SaleHeaderService saleHeaderService) {
        this.saleHeaderService = saleHeaderService;
    }

    @PostMapping
    public void createHeader(@RequestBody SaleHeader saleHeader){
        saleHeaderService.createHeader(saleHeader);
    }

    @GetMapping
    public List<SaleHeader> getAllHeaders(){
        return saleHeaderService.getAllSaleHeaders();
    }
}
