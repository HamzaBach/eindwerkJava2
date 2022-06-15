package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.Supplier;
import com.example.eindwerkJava2.model.dto.OrderDto;
import com.example.eindwerkJava2.service.ArticleService;
import com.example.eindwerkJava2.service.ArticleSupplierService;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


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
        SuccessEvaluator<OrderSupplierHeader> findOrderSuccess = orderSupplierHeaderService.findById(orderHeaderId);
        if (findOrderSuccess.getIsSuccessfull()) {
            OrderSupplierHeader orderSupplierHeader = findOrderSuccess.getEntity();
            Supplier supplier = orderSupplierHeader.getSupplier();
            List<OrderDto> orderDtos = orderSupplierDetailService.getOrderDtos(orderSupplierHeader);
            Map<String, Double> totals = orderSupplierDetailService.getTotalPriceFullOrder(orderDtos);

            model.addAttribute("grandTotalExclVat",totals.get("totalPriceExclVat"));
            model.addAttribute("grandTotalInclVat",totals.get("totalPriceInclVat"));
            model.addAttribute("grandTotalVat",totals.get("totalVatAmount"));
            model.addAttribute("orderSupplierDetail", new OrderSupplierDetail());
            model.addAttribute("orderheader", orderSupplierHeader);
            model.addAttribute("articles", articleService.getArticlesWhereSupplierIsPreferredSupplier(supplier));
            model.addAttribute("orderDtos", orderDtos);
            model.addAttribute("lineCounter", (orderSupplierDetailService.getOrderDetailsFromHeader(orderSupplierHeader).size() + 1));
        } else {
            model.addAttribute("error", findOrderSuccess.getMessage());
        }
        return "/forms/form_order_detail";
    }

    @PostMapping("/saveOrderDetail")
    public String saveDetail(@ModelAttribute("orderSupplierDetail") OrderDto orderDto, RedirectAttributes redirAttrs) {
        OrderSupplierDetail orderSupplierDetail = orderDto.convertOrderDtoToOrderSupplierDetail();
        this.orderSupplierDetailService.updateExpectedQuantity(orderSupplierDetail);
        return "redirect:/orderdetail/" + orderSupplierDetail.getOrderSupplierHeader().getOrderSupplierId();
    }

    @GetMapping("delete/orderdetail/{orderSupplierDetailId}")
    public String deleteOrderLine(@PathVariable("orderSupplierDetailId") Long orderSupplierDetailId, Model model) {
        OrderSupplierDetail orderSupplierDetail = orderSupplierDetailService.getById(orderSupplierDetailId).get();
        this.orderSupplierDetailService.deleteOrderLine(orderSupplierDetail);
        return "redirect:/orderdetail/" + orderSupplierDetail.getOrderSupplierHeader().getOrderSupplierId();
    }


}
