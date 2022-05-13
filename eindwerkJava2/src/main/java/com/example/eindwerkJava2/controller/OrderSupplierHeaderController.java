package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import com.example.eindwerkJava2.service.SupplierService;
import com.example.eindwerkJava2.wrappers.OrderSupplierHeaderSuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import com.example.eindwerkJava2.wrappers.SupplierSuccess;
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
    public String viewOrderSupplier(Model model) {
        OrderSupplierHeaderSuccess success = orderSupplierHeaderService.getOrderSupplierHeaders();
        if (success.getIsSuccessfull()) {
            model.addAttribute("orderSupplierList", success.getOrderSupplierHeaders());
        } else {
            model.addAttribute("error", success.getMessage());
        }
        return "orderSupplier";
    }

    @GetMapping("/viewOrderSupplier/{orderSupplierId}")
    public String showOrderSupplierForm(@PathVariable("orderSupplierId") Long orderSupplierId, Model model) {
        OrderSupplierHeaderSuccess findOrderSuccess = orderSupplierHeaderService.findById(orderSupplierId);
        if (findOrderSuccess.getIsSuccessfull()) {
            OrderSupplierHeader orderSupplierHeader = findOrderSuccess.getOrderSupplierHeader();
            model.addAttribute("orderHeader", orderSupplierHeader);
            model.addAttribute("suppliersList", orderSupplierHeader.getSupplier());
        } else {
            model.addAttribute("error", findOrderSuccess.getMessage());
        }
        return "/forms/form_order_detail";
    }

    @GetMapping("/new/order")
    public String AddOrderSupplierForm(Model model) {
        SupplierSuccess supplierSuccess = supplierService.getAllSuppliers();
        model.addAttribute("OrderSupplierHeader", new OrderSupplierHeader());
        model.addAttribute("suppliersList", supplierSuccess.getSuppliers());
        return "/forms/form_order_detail";
    }

    @GetMapping("/new/orderSupplier")
    public String chooseSupplierForm(Model model) {
        SupplierSuccess supplierSuccess = supplierService.getAllSuppliers();
        model.addAttribute("OrderSupplierHeader", new OrderSupplierHeader());
        model.addAttribute("suppliersList", supplierSuccess.getSuppliers());
        return "new_order_supplier_header";
    }

    @GetMapping("save/orderHeader/{supplierId}")
    public String createOrderHeadder(@PathVariable("supplierId") Long supplierId, Model model) {
        SupplierSuccess findSupplierSuccess = supplierService.findById(supplierId);
        if (findSupplierSuccess.getIsSuccessfull()) {
            SuccessObject isSaveOrderSuccessful = orderSupplierHeaderService.save(new OrderSupplierHeader(findSupplierSuccess.getSupplier(), LocalDate.now()));
            if (!isSaveOrderSuccessful.getIsSuccessfull()) {
                model.addAttribute("error", isSaveOrderSuccessful.getMessage());
            }
        }
        viewOrderSupplier(model);
        return "orderSupplier";
    }

    @PostMapping("/SaveOrderHeadSupplier")
    public String SaveOrderHeadSupplier(@ModelAttribute("OrderSupplierHeader") OrderSupplierHeader orderSupplierHeader, RedirectAttributes redirectAttributes) {
        SuccessObject isSaveSuccessful = orderSupplierHeaderService.save(orderSupplierHeader);
        if (isSaveSuccessful.getIsSuccessfull()) {
            Long orderSupplierId = orderSupplierHeaderService.getMaxId();
            redirectAttributes.addAttribute("orderSupplierId", orderSupplierId);
        } else {
            redirectAttributes.addAttribute("error", isSaveSuccessful.getMessage());
        }
        return "redirect:/viewOrderSupplier/{orderSupplierId}";
    }

    @GetMapping("/generatePdf/{orderSupplierId}")
    public String generatePdf(@PathVariable("orderSupplierId") Long orderHeaderId, RedirectAttributes redirAttr) {
        OrderSupplierHeaderSuccess findOrderSuccess = orderSupplierHeaderService.findById(orderHeaderId);
        if (findOrderSuccess.getIsSuccessfull()) {
            List<OrderSupplierDetail> orderList = orderSupplierDetailService.getCombinedDetailLineList(findOrderSuccess.getOrderSupplierHeader());
            orderSupplierHeaderService.makePdf(orderHeaderId, orderList);
        } else {
            redirAttr.addFlashAttribute("error", findOrderSuccess.getMessage());
        }
        return "redirect:/orderSupplier";
    }

    @GetMapping("/closeOrder/{orderSupplierId}")
    public String closeOrder(@PathVariable("orderSupplierId") Long orderHeaderId, Model model, RedirectAttributes redirAttr) {
        SuccessObject isCloseOrderSuccessful = orderSupplierHeaderService.closeOrder(orderHeaderId);
        if (isCloseOrderSuccessful.getIsSuccessfull()) {
            redirAttr.addFlashAttribute("success", isCloseOrderSuccessful.getMessage());
        } else {
            redirAttr.addFlashAttribute("error", isCloseOrderSuccessful.getMessage());
        }
        return "redirect:/orderSupplier";

    }

}
