package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.OrderSupplierHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSupplierHeaderRepository extends JpaRepository<OrderSupplierHeader, Long> {
}
