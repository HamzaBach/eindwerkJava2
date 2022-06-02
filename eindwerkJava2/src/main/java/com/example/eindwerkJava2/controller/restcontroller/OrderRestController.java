package com.example.eindwerkJava2.controller.restcontroller;


import com.example.eindwerkJava2.model.*;
import com.example.eindwerkJava2.model.dto.OrderReceiveDTO;
import com.example.eindwerkJava2.service.LocationService;
import com.example.eindwerkJava2.service.OrderSupplierDetailService;
import com.example.eindwerkJava2.service.OrderSupplierHeaderService;
import com.example.eindwerkJava2.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderRestController {

    private final OrderSupplierHeaderService orderSupplierHeaderService;
    private final OrderSupplierDetailService orderSupplierDetailService;
    private final LocationService locationService;
    private final UserService userService;

    public OrderRestController(OrderSupplierHeaderService orderSupplierHeaderService, OrderSupplierDetailService orderSupplierDetailService, LocationService locationService, UserService userService) {
        this.orderSupplierHeaderService = orderSupplierHeaderService;
        this.orderSupplierDetailService = orderSupplierDetailService;
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping()
    private String getOrders() throws JSONException {
        List<OrderSupplierHeader> orders = orderSupplierHeaderService.getAllClosedOrders().getEntities();

        JSONArray ja = new JSONArray();

        for (OrderSupplierHeader order : orders) {
            JSONObject jo = new JSONObject();
            jo.put("id", order.getOrderSupplierId());
            jo.put("orderNumber", order.getOrderNumber());
            jo.put("supplier", order.getSupplier().getSupplierName());
            ja.put(jo);
        }
        JSONObject json = new JSONObject();
        json.put("orders", ja);
        return json.toString();
    }

    @GetMapping(path = "{orderNumber}")
    private String getOrderDetailsFromHeader(@PathVariable String orderNumber) throws JSONException {
        OrderSupplierHeader orderHeader = orderSupplierHeaderService.findByOrderNumber(orderNumber);
        List<OrderSupplierDetail> orderDetailsFromHeader = orderSupplierDetailService.getOrderDetailsFromHeader(orderHeader);

        JSONArray ja = new JSONArray();

        for (OrderSupplierDetail orderDetail : orderDetailsFromHeader) {

                JSONObject jo = new JSONObject();
                jo.put("id", orderDetail.getOrderSupplierDetailId());
                jo.put("article", orderDetail.getArticle().getArticleName());
                jo.put("expectedAmount", orderDetail.getDeltaQuantity());
                ja.put(jo);

        }
        JSONObject json = new JSONObject();
        json.put("orderDetails", ja);
        return json.toString();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public String createHeader(@RequestBody ReceiveOrderLineDto receiveOrderLineDto) throws JSONException {
        OrderSupplierDetail orderDetail = orderSupplierDetailService.getById(receiveOrderLineDto.getId()).get();
        orderDetail.setReceivedQuantity(receiveOrderLineDto.getReceivedQuantity()+orderDetail.getReceivedQuantity());
        orderDetail.setDeltaQuantity(orderDetail.getDeltaQuantity()- orderDetail.getReceivedQuantity());


        OrderReceiveDTO orderReceiveDTO = new OrderReceiveDTO();
        orderReceiveDTO.setOrderSupplierDetailId(receiveOrderLineDto.getId());
        orderReceiveDTO.setReceivedQuantity(receiveOrderLineDto.getReceivedQuantity());
        orderReceiveDTO.setLocation(locationService.findByLocationId(receiveOrderLineDto.getLocation()));
        orderReceiveDTO.setUser(userService.findById(receiveOrderLineDto.getUser()).getEntity());

        orderSupplierDetailService.save(orderDetail, orderReceiveDTO);

        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("id", receiveOrderLineDto.getId());
        jo.put("amount", receiveOrderLineDto.getReceivedQuantity());
        jo.put("location", receiveOrderLineDto.getLocation());
        jo.put("user", receiveOrderLineDto.getUser());
        ja.put(jo);
        JSONObject json = new JSONObject();
        json.put("receive", ja);
        return json.toString();

    }


}
