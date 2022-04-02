package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.repositories.OrderSupplierDetailRepository;
import org.apache.tools.ant.types.resources.selectors.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderSupplierDetailService {
    private OrderSupplierDetailRepository orderSupplierDetailRepository;
    @Autowired
    OrderSupplierHeaderService orderSupplierHeaderService;

    public OrderSupplierDetailService(OrderSupplierDetailRepository orderSupplierDetailRepository) {
        this.orderSupplierDetailRepository = orderSupplierDetailRepository;
    }

    public List<OrderSupplierDetail> getAllOrderDetails() {
        return orderSupplierDetailRepository.findAll();
    }


    public void save(OrderSupplierDetail orderSupplierDetail) {
        orderSupplierDetailRepository.save(orderSupplierDetail);
    }

    public List<OrderSupplierDetail> getOrderDetailsFromHeader(OrderSupplierHeader orderSupplierHeader) {
        List<OrderSupplierDetail> orderSupplierDetailList = getAllOrderDetails();
        List<OrderSupplierDetail> resultList = new ArrayList<>();

        for (OrderSupplierDetail orderSupplierDetail:orderSupplierDetailList){
            if(orderSupplierDetail.getOrderSupplierHeader().equals(orderSupplierHeader)){
                resultList.add(orderSupplierDetail);
            }
        }
        return resultList;

    }

    public Optional<OrderSupplierDetail> getById(Long id) {
        return orderSupplierDetailRepository.findById(id);
    }

    public void deleteOrderLine(OrderSupplierDetail orderSupplierDetail) {
        orderSupplierDetailRepository.delete(orderSupplierDetail);
    }


}
