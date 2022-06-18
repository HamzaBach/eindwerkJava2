package com.example.eindwerkJava2.controller.restcontroller;

import com.example.eindwerkJava2.model.SaleHeader;
import com.example.eindwerkJava2.model.dto.CreateSaleLineDto;
import com.example.eindwerkJava2.model.dto.SaleHeaderDto;
import com.example.eindwerkJava2.service.SaleHeaderService;
import com.example.eindwerkJava2.service.SaleLineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sale-lines")
public class SaleLineRestController {

    private final SaleLineService saleLineService;
    private final SaleHeaderService saleHeaderService;

    public SaleLineRestController(SaleLineService saleLineService, SaleHeaderService saleHeaderService) {
        this.saleLineService = saleLineService;
        this.saleHeaderService = saleHeaderService;
    }

    @PostMapping
    public void createSaleLine(@RequestBody CreateSaleLineDto createSaleLineDto){
        saleLineService.saveSaveLine(createSaleLineDto);
    }

    @GetMapping("{saleHeaderId}")
    public SaleHeaderDto getSaleLinesFromHeader(@PathVariable Long saleHeaderId){
        SaleHeader saleHeader = saleHeaderService.getSaleHeaderById(saleHeaderId);
        return new SaleHeaderDto(
                saleHeader.getSaleHeaderId(),
                saleHeader.getNameSalesPerson(),
                saleHeader.getDateOfOrder(),
                saleLineService.saleLineDtos(saleLineService.getAllSaleLinesFromHeader(saleHeader.getSaleHeaderId())),
                saleHeader.getTotalPrice(),
                saleHeader.getStore()
                );
    }

}
