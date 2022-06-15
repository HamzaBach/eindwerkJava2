package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.model.dto.OrderDto;
import com.example.eindwerkJava2.model.dto.OrderReceiveDTO;
import com.example.eindwerkJava2.repositories.OrderSupplierDetailRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class OrderSupplierDetailService {
    @Autowired
    private final MutationServiceImpl mutationServiceImpl;
    @Autowired
    private final OrderSupplierDetailRepository orderSupplierDetailRepository;
    @Autowired
    private final LocationService locationService;
    @Autowired
    private final OrderSupplierHeaderService orderSupplierHeaderService;
    @Autowired
    private final ArticleSupplierService articleSupplierService;

    public OrderSupplierDetailService(MutationServiceImpl mutationServiceImpl, OrderSupplierDetailRepository orderSupplierDetailRepository,
                                      LocationService locationService, OrderSupplierHeaderService orderSupplierHeaderService,
                                      ArticleSupplierService articleSupplierService) {
        this.mutationServiceImpl = mutationServiceImpl;
        this.orderSupplierDetailRepository = orderSupplierDetailRepository;
        this.locationService = locationService;
        this.orderSupplierHeaderService=orderSupplierHeaderService;
        this.articleSupplierService=articleSupplierService;
    }

    public List<OrderSupplierDetail> getAllOrderDetails() {
        return orderSupplierDetailRepository.findAll();
    }

    public void updateExpectedQuantity(OrderSupplierDetail orderSupplierDetail) {
        List<OrderSupplierDetail> orderSupplierDetailList = getOrderDetailsFromHeader(orderSupplierDetail.getOrderSupplierHeader());
        boolean check = false;
        for (OrderSupplierDetail order : orderSupplierDetailList) {
            if (order.getArticle().equals(orderSupplierDetail.getArticle())) {
                OrderSupplierDetail existingArticleOrderLine = orderSupplierDetailRepository.getById(order.getOrderSupplierDetailId());
                existingArticleOrderLine.setExpectedQuantity(order.getExpectedQuantity() + orderSupplierDetail.getExpectedQuantity());
                existingArticleOrderLine.setDeltaQuantity(existingArticleOrderLine.getExpectedQuantity());
                if (checkLatestDate(existingArticleOrderLine.getExpectedDayOfDelivery(), orderSupplierDetail.getExpectedDayOfDelivery())) {
                    existingArticleOrderLine.setExpectedDayOfDelivery(orderSupplierDetail.getExpectedDayOfDelivery());
                }
                setArticlePurchasePrice(existingArticleOrderLine);
                orderSupplierDetailRepository.save(existingArticleOrderLine);
                check = true;
            }
        }
        if (!check) {
            orderSupplierDetail.setDeltaQuantity(orderSupplierDetail.getExpectedQuantity());
            setArticlePurchasePrice(orderSupplierDetail);
            orderSupplierDetailRepository.save(orderSupplierDetail);
        }
    }

    private void setArticlePurchasePrice(OrderSupplierDetail orderSupplierDetail){
        List<ArticleSupplier> articlesFromSupplier = articleSupplierService.getAllArticlesFromSupplier(orderSupplierDetail.getOrderSupplierHeader().getSupplier());
        for(ArticleSupplier articleSupplier:articlesFromSupplier){
            if(articleSupplier.getArticle().getArticleId()==orderSupplierDetail.getArticle().getArticleId()){
                orderSupplierDetail.setBuyPriceArticleExclVat(articleSupplier.getPurchasePrice());
                orderSupplierDetail.setCurrency(articleSupplier.getCurrency().getCurrency());
            }
        }
    }

    public List<OrderDto> getOrderDtos(OrderSupplierHeader orderSupplierHeader){

        //Adding DTO for orderSupplierDetail so that total price can be calculated:
        List<OrderDto> orderDtos = new ArrayList<>();
        List<OrderSupplierDetail> orderSupplierDetailList = getOrderDetailsFromHeader(orderSupplierHeader);
        for(OrderSupplierDetail orderSupplierDetail:orderSupplierDetailList){
            OrderDto orderDto = new OrderDto();
            orderDto.convertOrderSupplierDetailToOrderDto(orderSupplierDetail);
            orderDto.setTotalPriceExclVat(getTotalPriceForOrderLineExclVat(orderSupplierDetail));
            orderDto.setTotalPriceInclVat(getTotalPriceForOrderlineInclVat(orderSupplierDetail));
            Double vatRate = orderDto.getArticle().getCategory().getVat().getVatRate()*100;
            orderDto.setVatRate(vatRate+" %");
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public Double getTotalPriceForOrderLineExclVat(OrderSupplierDetail orderSupplierDetail){
        return orderSupplierDetail.getExpectedQuantity()*orderSupplierDetail.getBuyPriceArticleExclVat();
    }

    public Double getTotalPriceForOrderlineInclVat(OrderSupplierDetail orderSupplierDetail){
        Double totalPriceExclVat = getTotalPriceForOrderLineExclVat(orderSupplierDetail);
        Double vatRate = orderSupplierDetail.getArticle().getCategory().getVat().getVatRate();
        Double vatAmount = totalPriceExclVat*vatRate;
        return totalPriceExclVat+vatAmount;
    }
    public Map<String,Double> getTotalPriceFullOrder(List<OrderDto> orderDtos){
        Double totalPriceExclVat = 0.0;
        Double totalVatAmount = 0.0;
        Double totalPriceInclVat = 0.0;
        for(OrderDto orderDto:orderDtos){
            totalPriceExclVat=totalPriceExclVat+orderDto.getTotalPriceExclVat();
            totalVatAmount=totalVatAmount+(orderDto.getTotalPriceInclVat()-orderDto.getTotalPriceExclVat());
            totalPriceInclVat=totalPriceInclVat+orderDto.getTotalPriceInclVat();
        }
        Map<String,Double> totals = new HashMap<>();
        totals.put("totalPriceExclVat",totalPriceExclVat);
        totals.put("totalPriceInclVat",totalPriceInclVat);
        totals.put("totalVatAmount",totalVatAmount);
        return totals;
    }

    private boolean checkLatestDate(LocalDate first, LocalDate second) {
        return second.isAfter(first);
    }


    public SuccessObject save(OrderSupplierDetail orderSupplierDetail, OrderReceiveDTO orderReceiveDTO) {
        SuccessObject isSaveSuccessful = new SuccessEvaluator<>();
        if(orderReceiveDTO.getReceivedQuantity()<=0){
            isSaveSuccessful.setIsSuccessfull(false);
            isSaveSuccessful.setMessage("Unable to save the order because the received quantity is set to: "+orderReceiveDTO.getReceivedQuantity());
        } else {
            orderSupplierDetailRepository.save(orderSupplierDetail);
            Mutation mutation = new Mutation();
            mutation.setAmount(orderReceiveDTO.getReceivedQuantity());
            mutation.setArticle(orderSupplierDetail.getArticle());
            mutation.setLocalDateTime(LocalDateTime.now());
            mutation.setComment("Order number: "+orderSupplierDetail.getOrderSupplierHeader().getOrderNumber());
            mutation.setLocation(orderReceiveDTO.getLocation());
            mutation.setUser(orderReceiveDTO.getUser());
            mutationServiceImpl.addStock(mutation);

            isSaveSuccessful.setIsSuccessfull(true);
            isSaveSuccessful.setMessage("Article "+orderSupplierDetail.getArticle().getArticleName()+" with an amount of "+orderReceiveDTO.getReceivedQuantity()+" was succesfully booked to location: "+orderReceiveDTO.getLocation().getLocationName());
        }
        return isSaveSuccessful;
    }

    public List<OrderSupplierDetail> getOrderDetailsFromHeader(OrderSupplierHeader orderSupplierHeader) {
        List<OrderSupplierDetail> orderSupplierDetailList = getAllOrderDetails();

        List<OrderSupplierDetail> resultList = orderSupplierDetailList.stream()
                .filter(orderSupplierDetail -> orderSupplierDetail.getOrderSupplierHeader().equals(orderSupplierHeader))
                .collect(Collectors.toList());


        return resultList;

    }

    public boolean checkIfOrderIsCompleted(OrderSupplierHeader orderSupplierHeader) {
        List<OrderSupplierDetail> detailList = getOrderDetailsFromHeader(orderSupplierHeader);
        int count = 0;
        for (OrderSupplierDetail orderLine : detailList) {
            count += orderLine.getDeltaQuantity();
        }
        return count >= 0;


    }

    public List<OrderSupplierDetail> getCombinedDetailLineList(OrderSupplierHeader orderSupplierHeader) {

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
