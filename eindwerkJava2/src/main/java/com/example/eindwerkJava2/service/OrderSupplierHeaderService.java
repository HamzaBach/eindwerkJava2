package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Cities;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.repositories.OrderSupplierHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderSupplierHeaderService {
    @Autowired
    OrderSupplierHeaderRepository orderSupplierHeaderRepository;

    public OrderSupplierHeaderService(OrderSupplierHeaderRepository orderSupplierHeaderRepository) {
        this.orderSupplierHeaderRepository = orderSupplierHeaderRepository;
    }

    public List<OrderSupplierHeader> getOrderSupplierHeaders(){
        return orderSupplierHeaderRepository.findAll();
    }
    public Optional<OrderSupplierHeader> findById(long id){
        return orderSupplierHeaderRepository.findById(id);
    }

}

