package com.example.eindwerkJava2.model.dto;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.OrderSupplierHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderReceiveDTO {
    private Long orderSupplierDetailId;
    private OrderSupplierHeader orderSupplierHeader;
    private Article article;
    private double expectedQuantity;
    private double deltaQuantity;
    private double receivedQuantity;
    private LocalDate expectedDayOfDelivery;
    private String orderlineNumber;
    private Location location;

    public List<OrderReceiveDTO> mapOrderSupplierDetailToDTO(List<OrderSupplierDetail> orderSupplierDetailList){
        List<OrderReceiveDTO> orderReceiveDTOS=new ArrayList<>();
        for(OrderSupplierDetail x:orderSupplierDetailList){
            OrderReceiveDTO orderReceiveDTO = new OrderReceiveDTO();
            orderReceiveDTO.setArticle(x.getArticle());
            orderReceiveDTO.setOrderlineNumber(x.getOrderlineNumber());
            orderReceiveDTO.setOrderSupplierDetailId(x.getOrderSupplierDetailId());
            orderReceiveDTO.setOrderSupplierHeader(x.getOrderSupplierHeader());
            orderReceiveDTO.setExpectedQuantity(x.getExpectedQuantity());
            orderReceiveDTO.setDeltaQuantity(x.getDeltaQuantity());
            orderReceiveDTO.setReceivedQuantity(x.getReceivedQuantity());
            orderReceiveDTO.setExpectedDayOfDelivery(x.getExpectedDayOfDelivery());
            orderReceiveDTO.setOrderSupplierDetailId(x.getOrderSupplierDetailId());
            orderReceiveDTOS.add(orderReceiveDTO);
        }
        return orderReceiveDTOS;
    }
}

