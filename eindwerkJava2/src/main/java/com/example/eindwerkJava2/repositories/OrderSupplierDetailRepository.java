package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSupplierDetailRepository extends JpaRepository<OrderSupplierDetail, Long> {

}
