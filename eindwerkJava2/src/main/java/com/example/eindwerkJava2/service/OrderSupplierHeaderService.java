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

    public Long getMaxId(){return orderSupplierHeaderRepository.getMaxId();}

    public void addSupplierHeader( OrderSupplierHeader orderSupplierHeader ){
        orderSupplierHeaderRepository.save(orderSupplierHeader);
    }

    public void save(OrderSupplierHeader orderSupplierHeader) {
        //Save it first so that id is created
        orderSupplierHeaderRepository.save(orderSupplierHeader);
        //Logic to retrieve the just saved object and construct an order number based on the id:
        Optional<OrderSupplierHeader> orderSupplierHeader1= orderSupplierHeaderRepository.findById(orderSupplierHeaderRepository.getMaxId());
        String supplierName=orderSupplierHeader1.get().getSupplier().getSupplierName();
        orderSupplierHeader1.get().setOrderNumber(supplierName+"-"+orderSupplierHeader1.get().getOrderSupplierId());
        //Resave same object but this time with orderNumber and DateOrderClosed:
        orderSupplierHeaderRepository.save(orderSupplierHeader1.get());
    }
}

