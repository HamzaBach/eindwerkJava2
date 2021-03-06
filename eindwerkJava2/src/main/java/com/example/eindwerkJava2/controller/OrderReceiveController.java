package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.model.dto.OrderReceiveDTO;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.service.LocationService;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

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
        SuccessEvaluator<OrderSupplierHeader> success = orderSupplierHeaderService.getAllClosedOrders();
        if (success.getIsSuccessfull()) {
            List<OrderSupplierHeader> orderSupplierHeaderList = success.getEntities();
            model.addAttribute("orderList", orderSupplierHeaderList);
        } else {
            model.addAttribute("error", success.getMessage());
        }
        return "orders_received";
    }

    @GetMapping(path = "/view/orderReceived/{orderId}")
    public String viewOrder(@PathVariable("orderId") Long orderId, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUserNameAndActiveUser(currentUser.getUsername(),1);
        OrderSupplierHeader orderSupplierHeader = orderSupplierHeaderService.findById(orderId).getEntity();
        List<OrderSupplierDetail> orderSupplierDetailList = orderSupplierDetailService.getOrderDetailsFromHeader(orderSupplierHeader);
        OrderReceiveDTO orderReceiveDTO = new OrderReceiveDTO();
        List<OrderReceiveDTO> orderReceiveDTOS=orderReceiveDTO.mapOrderSupplierDetailToDTO(orderSupplierDetailList);

        model.addAttribute("currentUser",user);
        model.addAttribute("orderheader", orderSupplierHeader);
        model.addAttribute("orderLines", orderSupplierDetailList);
        model.addAttribute("locationList", locationService.getNonSingleStorageLocations());

        for (OrderReceiveDTO orderLine : orderReceiveDTOS) {
            model.addAttribute("OrderReceiveDTO", orderLine);
        }
        return "forms/form_order_received";
    }

    @PostMapping("/saveReceive/{orderSupplierDetailId}")
    public String saveDetail(@ModelAttribute("OrderReceiveDTO") OrderReceiveDTO orderReceiveDTO,
                             @PathVariable("orderSupplierDetailId") Long orderSupplierDetailId, RedirectAttributes redirAttr) {
        OrderSupplierDetail orderLine = orderSupplierDetailService.getById(orderSupplierDetailId).get();
        orderLine.setReceivedQuantity(orderReceiveDTO.getReceivedQuantity()+orderLine.getReceivedQuantity());
        orderLine.setDeltaQuantity(orderLine.getExpectedQuantity()-orderLine.getReceivedQuantity());
        SuccessObject isSaveSuccessful = this.orderSupplierDetailService.save(orderLine,orderReceiveDTO);

        if(isSaveSuccessful.getIsSuccessfull()){
            redirAttr.addFlashAttribute("success",isSaveSuccessful.getMessage());
        } else {
            redirAttr.addFlashAttribute("error",isSaveSuccessful.getMessage());
        }

        return "redirect:/view/orderReceived/" + orderLine.getOrderSupplierHeader().getOrderSupplierId();
    }

    @GetMapping("/closeReceive/{orderSupplierId}")
    public String closeReceiving(@PathVariable("orderSupplierId") Long orderHeaderId, Model model, RedirectAttributes redirAttr) {
        SuccessEvaluator<OrderSupplierHeader> orderSupplierHeader = orderSupplierHeaderService.findById(orderHeaderId);
            orderSupplierHeader.getEntity().setDateOrderReceived(LocalDate.now());
            orderSupplierHeaderService.save(orderSupplierHeader.getEntity());
        return "redirect:/orderReceived";

    }


}
