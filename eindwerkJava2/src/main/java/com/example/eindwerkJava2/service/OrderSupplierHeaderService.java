package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.repositories.OrderSupplierHeaderRepository;
import org.hibernate.criterion.Order;
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

    public String getMaxId(Supplier supplier){return supplier.getSupplierName()+"-" + (orderSupplierHeaderRepository.getMaxId()+1);}

    public void addSupplierHeader( OrderSupplierHeader orderSupplierHeader ){
        orderSupplierHeaderRepository.save(orderSupplierHeader);
    }

    public void save(OrderSupplierHeader orderSupplierHeader) {
        orderSupplierHeaderRepository.save(orderSupplierHeader);
    }
}

