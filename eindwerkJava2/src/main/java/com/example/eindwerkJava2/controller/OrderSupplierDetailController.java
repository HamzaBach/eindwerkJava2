package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.ArticleSupplierService;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderSupplierDetailController {
    @Autowired
    OrderSupplierHeaderService orderSupplierHeaderService;
    @Autowired
    ArticleSupplierService articleSupplierService;
    @Autowired
    ArticleService articleService;


    private OrderSupplierDetailService orderSupplierDetailService;

    public OrderSupplierDetailController(OrderSupplierDetailService orderSupplierDetailService) {
        this.orderSupplierDetailService = orderSupplierDetailService;
    }

    @GetMapping("orderdetail/{orderSupplierId}")
    public String fillHeader(@PathVariable("orderSupplierId") Long orderHeaderId, Model model) {
        Supplier supplier = new Supplier();//retrieve from header
        OrderSupplierHeader orderSupplierHeader = orderSupplierHeaderService.findById(orderHeaderId).get();
        supplier = orderSupplierHeader.getSupplier();
        model.addAttribute("orderSupplierDetail", new OrderSupplierDetail());
        model.addAttribute("orderheader", orderSupplierHeaderService.findById(orderHeaderId).get());
        model.addAttribute("articles", articleSupplierService.getArticlesFromSupplier(supplier));
        return "/forms/form_order_detail";
    }

    @PostMapping("/saveOrderDetail")
    public String saveDetail(@ModelAttribute("orderSupplierDetail") OrderSupplierDetail orderSupplierDetail){
        this.orderSupplierDetailService.save(orderSupplierDetail);
        return "redirect:/orderSupplier";
    }


}
