package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import com.example.eindwerkJava2.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.time.LocalDate;

@Controller
public class OrderSupplierHeaderController {
    @Autowired
    OrderSupplierHeaderService orderSupplierHeaderService;
    @Autowired
    SupplierService supplierService;

    @GetMapping("/orderSupplier")
    public String viewCountries(Model model){
        model.addAttribute("orderSupplierList", orderSupplierHeaderService.getOrderSupplierHeaders());
        return "orderSupplier";
    }

    @GetMapping("/viewOrderSupplier/{orderSupplierId}")
    public String showOrderSupplierForm(@PathVariable("orderSupplierId") Long orderSupplierId, Model model){
        OrderSupplierHeader orderSupplierHeader =  orderSupplierHeaderService.findById(orderSupplierId).get();
        model.addAttribute("OrderSupplierHeader", orderSupplierHeader);
        model.addAttribute("suppliersList", orderSupplierHeader.getSupplier());
        return "/forms/form_order_detail";
    }



    @GetMapping("/new/order")
    public String AddOrderSupplierForm(Model model){
        model.addAttribute("OrderSupplierHeader",new OrderSupplierHeader());
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        return "/forms/form_order_detail";
    }
    @PostMapping("/SaveOrderHeadSupplier")
    public String SaveOrderHeadSupplier(@ModelAttribute("OrderSupplierHeader") OrderSupplierHeader orderSupplierHeader,Model model)
    {
        orderSupplierHeader.setDateOrderClosed(orderSupplierHeader.getDateOfOrder().plusDays(14));
        orderSupplierHeader.setOrderNumber(orderSupplierHeaderService.getMaxId(orderSupplierHeader.getSupplier()));
        orderSupplierHeaderService.save(orderSupplierHeader);
        //model.addAttribute("orderSupplierId", orderSupplierHeader.getOrderSupplierId());
        return "redirect:/viewOrderSupplier/{orderSupplierId}";
    }






}
