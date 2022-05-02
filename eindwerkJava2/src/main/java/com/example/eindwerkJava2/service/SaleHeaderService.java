package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.SaleHeader;
import com.example.eindwerkJava2.repositories.SaleHeaderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleHeaderService {
    private final SaleHeaderRepository saleHeaderRepository;

    public SaleHeaderService(SaleHeaderRepository saleHeaderRepository) {
        this.saleHeaderRepository = saleHeaderRepository;
    }

    public void createHeader(SaleHeader saleHeader){
        saleHeader.setDateOfOrder(LocalDateTime.now());
        saleHeaderRepository.save(saleHeader);
    }

    public List<SaleHeader> getAllSaleHeaders(){
        return saleHeaderRepository.findAll();
    }

    public SaleHeader getSaleHeaderById(Long saleHeaderId) {
        return saleHeaderRepository.findById(saleHeaderId).get();
    }
}
