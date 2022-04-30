package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import com.example.eindwerkJava2.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
public class OrderSupplierHeaderController {
    @Autowired
    OrderSupplierHeaderService orderSupplierHeaderService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    OrderSupplierDetailService orderSupplierDetailService;

    @GetMapping("/orderSupplier")
    public String viewOrderSupplier(Model model){
        model.addAttribute("orderSupplierList", orderSupplierHeaderService.getOrderSupplierHeaders());
        return "orderSupplier";
    }

    @GetMapping("/viewOrderSupplier/{orderSupplierId}")
    public String showOrderSupplierForm(@PathVariable("orderSupplierId") Long orderSupplierId, Model model){
        OrderSupplierHeader orderSupplierHeader =  orderSupplierHeaderService.findById(orderSupplierId).get();
        model.addAttribute("orderHeader", orderSupplierHeader);
        model.addAttribute("suppliersList", orderSupplierHeader.getSupplier());
        return "/forms/form_order_detail";
    }

    @GetMapping("/new/order")
    public String AddOrderSupplierForm(Model model){
        model.addAttribute("OrderSupplierHeader",new OrderSupplierHeader());
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        return "/forms/form_order_detail";
    }

    @GetMapping("/new/orderSupplier")
    public String chooseSupplierForm(Model model) {
        model.addAttribute("OrderSupplierHeader", new OrderSupplierHeader());
        model.addAttribute("suppliersList", supplierService.getAllSuppliers());
        return "new_order_supplier_header";
    }

    @GetMapping("save/orderHeader/{supplierId}")
    public String createOrderHeadder(@PathVariable("supplierId") Long supplierId, Model model){
        orderSupplierHeaderService.save(new OrderSupplierHeader(supplierService.findById(supplierId).get(),LocalDate.now()));
        viewOrderSupplier(model);
        return "orderSupplier";
    }

    @PostMapping("/SaveOrderHeadSupplier")
    public String SaveOrderHeadSupplier(@ModelAttribute("OrderSupplierHeader") OrderSupplierHeader orderSupplierHeader, RedirectAttributes redirectAttributes)
    {
        orderSupplierHeaderService.save(orderSupplierHeader);
        Long orderSupplierId = orderSupplierHeaderService.getMaxId();
        redirectAttributes.addAttribute("orderSupplierId", orderSupplierId);
        return "redirect:/viewOrderSupplier/{orderSupplierId}";
    }

    @GetMapping("/generatePdf/{orderSupplierId}")
    public String generatePdf(@PathVariable("orderSupplierId") Long orderHeaderId, Model model){
        List<OrderSupplierDetail> orderList = orderSupplierDetailService.getCombinedDetailLineList(orderSupplierHeaderService.findById(orderHeaderId).get());
        orderSupplierHeaderService.makePdf(orderHeaderId,orderList);
        return "redirect:/orderSupplier";
    }

    @GetMapping("/closeOrder/{orderSupplierId}")
    public String closeOrder(@PathVariable("orderSupplierId") Long orderHeaderId, Model model){
        orderSupplierHeaderService.closeOrder(orderHeaderId);
        return "redirect:/orderSupplier";
    }

}
