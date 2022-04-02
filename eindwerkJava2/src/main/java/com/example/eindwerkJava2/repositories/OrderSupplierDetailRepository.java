package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.OrderSupplierDetail;
import com.example.eindwerkJava2.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSupplierDetailRepository extends JpaRepository<OrderSupplierDetail, Long> {
    @Query(value = "SELECT article_article_id, sum(quantity) FROM order_supplier_detail group by article_article_id", nativeQuery = true)
    List<OrderSupplierDetail> getSumOfDetails();

}
