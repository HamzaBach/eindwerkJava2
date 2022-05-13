package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import com.example.eindwerkJava2.wrappers.OrderSupplierHeaderSuccess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderReceiveController {
    private final OrderSupplierHeaderService orderSupplierHeaderService;
    private final OrderSupplierDetailService orderSupplierDetailService;

    public OrderReceiveController(OrderSupplierHeaderService orderSupplierHeaderService, OrderSupplierDetailService orderSupplierDetailService) {

        this.orderSupplierHeaderService = orderSupplierHeaderService;
        this.orderSupplierDetailService = orderSupplierDetailService;
    }

    @GetMapping(path = "orderReceived")
    public String getAllOrders(Model model) {
        OrderSupplierHeaderSuccess getClosedOrdersSuccess = orderSupplierHeaderService.getAllClosedOrders();
        if(!getClosedOrdersSuccess.getIsSuccessfull()){
            model.addAttribute("error",getClosedOrdersSuccess.getMessage());
        }
        List<OrderSupplierHeader> orderSupplierHeaderList = getClosedOrdersSuccess.getOrderSupplierHeaders();
        List<OrderSupplierHeader> resultList =  orderSupplierHeaderList.stream().filter(orderSupplierDetailService::checkIfOrderIsCompleted).collect(Collectors.toList());
        model.addAttribute("orderList", resultList);

        return "orders_received";
    }

    @GetMapping(path = "/view/orderReceived/{orderId}")
    public String viewOrder(@PathVariable("orderId") Long orderId, Model model) {
        OrderSupplierHeaderSuccess findOrderSuccess = orderSupplierHeaderService.findById(orderId);
        if(findOrderSuccess.getIsSuccessfull()){
            OrderSupplierHeader orderSupplierHeader = findOrderSuccess.getOrderSupplierHeader();
            List<OrderSupplierDetail> orderSupplierDetailList = orderSupplierDetailService.getOrderDetailsFromHeader(orderSupplierHeader);
            model.addAttribute("orderheader", orderSupplierHeader);
            model.addAttribute("orderLines", orderSupplierDetailList);
            for (OrderSupplierDetail orderLine : orderSupplierDetailList) {
                model.addAttribute("orderSupplierDetail", orderLine);
            }
        } else {
            model.addAttribute("error",findOrderSuccess.getMessage());
        }
        return "forms/form_order_received";
    }

    @PostMapping("/saveReceive/{orderLineId}")
    public String saveDetail(@ModelAttribute("orderSupplierDetail") OrderSupplierDetail orderSupplierDetail,
                             @PathVariable("orderLineId") Long orderLineId) {
        OrderSupplierDetail orderLine = orderSupplierDetailService.getById(orderLineId).get();
        orderLine.setReceivedQuantity(orderSupplierDetail.getReceivedQuantity()+orderLine.getReceivedQuantity());
        orderLine.setDeltaQuantity(orderLine.getExpectedQuantity()-orderLine.getReceivedQuantity());
        this.orderSupplierDetailService.save(orderLine);
        return "redirect:/view/orderReceived/" + orderLine.getOrderSupplierHeader().getOrderSupplierId();
    }


}
