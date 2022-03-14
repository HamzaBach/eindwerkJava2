package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderSupplierHeaderController {
    @Autowired
    OrderSupplierHeaderService orderSupplierHeaderService;

    @GetMapping("/orderSupplier")
    public String viewCountries(Model model){
        model.addAttribute("orderSupplierList", orderSupplierHeaderService.getOrderSupplierHeaders());
        return "orderSupplier";
    }

    @GetMapping("viewOrderSupplier/{orderSupplierId}")
    public String showOrderSupplierForm(@PathVariable("orderSupplierId") Long orderSupplierId, Model model){
        OrderSupplierHeader orderSupplierHeader =  orderSupplierHeaderService.findById(orderSupplierId).get();
        model.addAttribute("orderSupplier", orderSupplierHeader);
        return "order_detail";
    }





}
