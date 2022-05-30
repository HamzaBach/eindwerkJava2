package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.OrderSupplierHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSupplierHeaderRepository extends JpaRepository<OrderSupplierHeader, Long> {
    @Query(value = "select MAX(order_supplier_id) AS Max_Id from order_supplier_header",nativeQuery = true)
    Long getMaxId();

    @Query(value = "select *  from order_supplier_header where date_order_received is null and date_order_closed is not null;",nativeQuery = true)
    List<OrderSupplierHeader> getAllClosedOrders();
    OrderSupplierHeader findByOrderNumber(String orderNumber);
    Boolean existsByOrderNumber(String orderNumber);

}
