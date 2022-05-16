package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.repositories.OrderSupplierDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class OrderSupplierDetailService {
    private final MutationServiceImpl mutationServiceImpl;
    private OrderSupplierDetailRepository orderSupplierDetailRepository;
    private final LocationService locationService;
    @Autowired
    OrderSupplierHeaderService orderSupplierHeaderService;

    public OrderSupplierDetailService(MutationServiceImpl mutationServiceImpl, OrderSupplierDetailRepository orderSupplierDetailRepository, LocationService locationService) {
        this.mutationServiceImpl = mutationServiceImpl;
        this.orderSupplierDetailRepository = orderSupplierDetailRepository;
        this.locationService = locationService;
    }

    public List<OrderSupplierDetail> getAllOrderDetails() {
        return orderSupplierDetailRepository.findAll();
    }

    public void updateExpectedQuantity(OrderSupplierDetail orderSupplierDetail){
        List<OrderSupplierDetail> orderSupplierDetailList = getOrderDetailsFromHeader(orderSupplierDetail.getOrderSupplierHeader());
        boolean check = false;
        for(OrderSupplierDetail order: orderSupplierDetailList){
            if(order.getArticle().equals(orderSupplierDetail.getArticle())){
                OrderSupplierDetail existingArticleOrderLine = orderSupplierDetailRepository.getById(order.getOrderSupplierDetailId());
                existingArticleOrderLine.setExpectedQuantity(order.getExpectedQuantity()+orderSupplierDetail.getExpectedQuantity()  );
                existingArticleOrderLine.setDeltaQuantity(existingArticleOrderLine.getExpectedQuantity());
                if (checkLatestDate(existingArticleOrderLine.getExpectedDayOfDelivery(),orderSupplierDetail.getExpectedDayOfDelivery())){
                    existingArticleOrderLine.setExpectedDayOfDelivery(orderSupplierDetail.getExpectedDayOfDelivery());
                }
                orderSupplierDetailRepository.save(existingArticleOrderLine);
                check = true;
            }
        }
        if(!check){
            orderSupplierDetail.setDeltaQuantity(orderSupplierDetail.getExpectedQuantity());
            orderSupplierDetailRepository.save(orderSupplierDetail);
        }
    }
    private boolean checkLatestDate(LocalDate first, LocalDate second){
        return second.isAfter(first);
    }


    public void save(OrderSupplierDetail orderSupplierDetail) {
            orderSupplierDetailRepository.save(orderSupplierDetail);
        Mutation mutation = new Mutation();
        mutation.setAmount(orderSupplierDetail.getReceivedQuantity());
        mutation.setArticle(orderSupplierDetail.getArticle());
        Location location = locationService.findByLocationId(1L);
        mutation.setLocationTo(location);
        mutation.setWarehouseTo(location.getWarehouse());
        mutation.setComment(orderSupplierDetail.getOrderSupplierHeader().getOrderNumber());
        mutation.setLocationFrom(locationService.findByLocationId(2L));
        mutationServiceImpl.addStock(mutation);
    }

    public List<OrderSupplierDetail> getOrderDetailsFromHeader(OrderSupplierHeader orderSupplierHeader) {
        List<OrderSupplierDetail> orderSupplierDetailList = getAllOrderDetails();

        List<OrderSupplierDetail> resultList = orderSupplierDetailList.stream()
                .filter(orderSupplierDetail -> orderSupplierDetail.getOrderSupplierHeader().equals(orderSupplierHeader))
                .collect(Collectors.toList());


        return resultList;

    }

    public boolean checkIfOrderIsCompleted(OrderSupplierHeader orderSupplierHeader){
        List<OrderSupplierDetail> detailList = getOrderDetailsFromHeader(orderSupplierHeader);
        int count = 0;
        for(OrderSupplierDetail orderLine: detailList){
            count+=orderLine.getDeltaQuantity();
        }
        return count != 0;


    }

    public List<OrderSupplierDetail> getCombinedDetailLineList(OrderSupplierHeader orderSupplierHeader){

        List<OrderSupplierDetail> detailList = getOrderDetailsFromHeader(orderSupplierHeader);
        Map<Article, Double> quantityPerArticle = detailList.stream()
                .collect(groupingBy(OrderSupplierDetail::getArticle, summingDouble(OrderSupplierDetail::getExpectedQuantity)));

        List<OrderSupplierDetail> checkDate = getOrderDetailsFromHeader(orderSupplierHeader);
        Map<Article, Optional<OrderSupplierDetail>> maxDate = checkDate.stream()
                .collect(groupingBy(OrderSupplierDetail::getArticle,
                        maxBy(Comparator.comparing(OrderSupplierDetail::getExpectedDayOfDelivery))));


        int count = 1;
        List<OrderSupplierDetail> resultList = new ArrayList<>();
        for (Map.Entry<Article, Double> entry : quantityPerArticle.entrySet()) {
            resultList.add(new OrderSupplierDetail(
                    entry.getKey(),
                    entry.getValue(),
                    maxDate.get(entry.getKey()).get().getExpectedDayOfDelivery(),
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
