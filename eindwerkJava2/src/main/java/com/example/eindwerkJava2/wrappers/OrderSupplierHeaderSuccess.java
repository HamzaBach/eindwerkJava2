package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.OrderSupplierHeader;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderSupplierHeaderSuccess extends SuccessObject{
    private OrderSupplierHeader orderSupplierHeader;
    private List<OrderSupplierHeader> orderSupplierHeaders;
}
