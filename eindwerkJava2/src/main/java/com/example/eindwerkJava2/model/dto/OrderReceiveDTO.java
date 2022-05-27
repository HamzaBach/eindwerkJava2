package com.example.eindwerkJava2.model.dto;

import com.example.eindwerkJava2.model.*;
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
    private double receivedQuantity;
    private Location location;
    private User user;

    public List<OrderReceiveDTO> mapOrderSupplierDetailToDTO(List<OrderSupplierDetail> orderSupplierDetailList){
        List<OrderReceiveDTO> orderReceiveDTOS=new ArrayList<>();
        for(OrderSupplierDetail x:orderSupplierDetailList){
            OrderReceiveDTO orderReceiveDTO = new OrderReceiveDTO();
            orderReceiveDTO.setOrderSupplierDetailId(x.getOrderSupplierDetailId());
            orderReceiveDTO.setReceivedQuantity(x.getReceivedQuantity());
            orderReceiveDTO.setOrderSupplierDetailId(x.getOrderSupplierDetailId());
            orderReceiveDTOS.add(orderReceiveDTO);
        }
        return orderReceiveDTOS;
    }
}

