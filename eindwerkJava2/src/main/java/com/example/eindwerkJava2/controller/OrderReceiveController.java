package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.model.dto.OrderReceiveDTO;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.service.LocationService;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderReceiveController {
    private final OrderSupplierHeaderService orderSupplierHeaderService;
    private final OrderSupplierDetailService orderSupplierDetailService;
    private final LocationService locationService;
    private final UserRepository userRepository;

    public OrderReceiveController(OrderSupplierHeaderService orderSupplierHeaderService, OrderSupplierDetailService orderSupplierDetailService, LocationService locationService, UserRepository userRepository) {

        this.orderSupplierHeaderService = orderSupplierHeaderService;
        this.orderSupplierDetailService = orderSupplierDetailService;
        this.locationService = locationService;
        this.userRepository=userRepository;
    }

    @GetMapping(path = "orderReceived")
    public String getAllOrders(Model model) {
        List<OrderSupplierHeader> orderSupplierHeaderList = orderSupplierHeaderService.getAllClosedOrders().getEntities();
        List<OrderSupplierHeader> resultList =  orderSupplierHeaderList.stream().filter(orderSupplierDetailService::checkIfOrderIsCompleted).collect(Collectors.toList());

        model.addAttribute("orderList", resultList);
        return "orders_received";
    }

    @GetMapping(path = "/view/orderReceived/{orderId}")
    public String viewOrder(@PathVariable("orderId") Long orderId, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUserName(currentUser.getUsername());
        OrderSupplierHeader orderSupplierHeader = orderSupplierHeaderService.findById(orderId).getEntity();
        List<OrderSupplierDetail> orderSupplierDetailList = orderSupplierDetailService.getOrderDetailsFromHeader(orderSupplierHeader);
        OrderReceiveDTO orderReceiveDTO = new OrderReceiveDTO();
        List<OrderReceiveDTO> orderReceiveDTOS=orderReceiveDTO.mapOrderSupplierDetailToDTO(orderSupplierDetailList);

        model.addAttribute("currentUser",user);
        model.addAttribute("orderheader", orderSupplierHeader);
        model.addAttribute("orderLines", orderReceiveDTOS);
        model.addAttribute("locationList", locationService.getAllLocations());

        for (OrderReceiveDTO orderLine : orderReceiveDTOS) {
            model.addAttribute("OrderReceiveDTO", orderLine);
        }
        return "forms/form_order_received";
    }

    @PostMapping("/saveReceive/{orderLineId}")
    public String saveDetail(@ModelAttribute("OrderReceiveDTO") OrderReceiveDTO orderReceiveDTO,
                             @PathVariable("orderLineId") Long orderLineId) {
        OrderSupplierDetail orderLine = orderSupplierDetailService.getById(orderLineId).get();
        orderLine.setReceivedQuantity(orderReceiveDTO.getReceivedQuantity()+orderLine.getReceivedQuantity());
        orderLine.setDeltaQuantity(orderLine.getExpectedQuantity()-orderLine.getReceivedQuantity());
        this.orderSupplierDetailService.save(orderLine,orderReceiveDTO.getLocation());
        return "redirect:/view/orderReceived/" + orderLine.getOrderSupplierHeader().getOrderSupplierId();
    }


}
