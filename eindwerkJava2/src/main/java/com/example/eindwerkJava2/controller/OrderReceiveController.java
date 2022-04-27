package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.service.OrderReceiveService;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderReceiveController {
    private final OrderReceiveService orderReceiveService;
    private final OrderSupplierHeaderService orderSupplierHeaderService;
    private final OrderSupplierDetailService orderSupplierDetailService;

    public OrderReceiveController(OrderReceiveService orderReceiveService, OrderSupplierHeaderService orderSupplierHeaderService, OrderSupplierDetailService orderSupplierDetailService) {
        this.orderReceiveService = orderReceiveService;
        this.orderSupplierHeaderService = orderSupplierHeaderService;
        this.orderSupplierDetailService = orderSupplierDetailService;
    }

    @GetMapping(path="orderReceived")
    public String getAllOrders(Model model){
        model.addAttribute("orderList", orderSupplierHeaderService.getOrderSupplierHeaders());
        return "orders_received";
    }

    @GetMapping(path="/view/orderReceived/{orderId}")
    public String viewOrder(@PathVariable("orderId") Long orderId, Model model){
        OrderSupplierHeader orderSupplierHeader = orderSupplierHeaderService.findById(orderId).get();
        List<OrderSupplierDetail> orderSupplierDetail = orderSupplierDetailService.getCombinedDetailLineList(orderSupplierHeader);
        model.addAttribute("order", orderSupplierDetail);
        return "forms/form_order_received";
    }
}
