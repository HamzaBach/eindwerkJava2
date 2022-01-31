package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;


    @Autowired
    public WarehouseService(WarehouseRepository WarehouseRepository) {
        this.warehouseRepository = WarehouseRepository;
    }

    public List<Warehouse> getAllWarehouses() {
        return this.warehouseRepository.findAll();
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }



}
