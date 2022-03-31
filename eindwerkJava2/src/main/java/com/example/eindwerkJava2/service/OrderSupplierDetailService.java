package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.repositories.OrderSupplierDetailRepository;
import org.apache.tools.ant.types.resources.selectors.Or;
import org.springframework.stereotype.Service;

@Service
public class OrderSupplierDetailService {
    private OrderSupplierDetailRepository orderSupplierDetailRepository;

    public OrderSupplierDetailService(OrderSupplierDetailRepository orderSupplierDetailRepository) {
        this.orderSupplierDetailRepository = orderSupplierDetailRepository;
    }

    public void save(OrderSupplierDetail orderSupplierDetail) {
        orderSupplierDetailRepository.save(orderSupplierDetail);
    }

}
