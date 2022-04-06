package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.ArticleSupplier;
import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.repositories.OrderSupplierDetailRepository;
import org.apache.tools.ant.types.resources.selectors.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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

        List<OrderSupplierDetail> resultList = orderSupplierDetailList.stream()
                .filter(orderSupplierDetail -> orderSupplierDetail.getOrderSupplierHeader().equals(orderSupplierHeader))
                .collect(Collectors.toList());



        return resultList;

    }

    public List<OrderSupplierDetail> getCombinedDetailLineList(OrderSupplierHeader orderSupplierHeader){

        List<OrderSupplierDetail> detailList = getOrderDetailsFromHeader(orderSupplierHeader);
        Map<Article, Integer> quantityPerArticle = detailList.stream()
                .collect(groupingBy(OrderSupplierDetail::getArticle, summingInt(OrderSupplierDetail::getQuantity)));

        int count = 1;
        List<OrderSupplierDetail> resultList = new ArrayList<>();
        for (Map.Entry<Article, Integer> entry : quantityPerArticle.entrySet()) {
            resultList.add(new OrderSupplierDetail(
                    entry.getKey(),
                    entry.getValue(),
                    LocalDate.now().plusDays(10),
                    String.valueOf(count++)));
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
