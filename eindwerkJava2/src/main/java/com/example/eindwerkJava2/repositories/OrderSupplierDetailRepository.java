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

    @Query(value="select article_article_id, sum(quantity), MAX(expectedd_day_of_delivery)" +
            " from order_supplier_detail " +
                " where order_supplier_header_order_supplier_id IN(" +
                    " select order_supplier_id from order_supplier_header " +
                    " where order_supplier_id = 1)" +
            " group by article_article_id;",nativeQuery = true)
    List<OrderSupplierDetail> getCombinedOrderLines();

}
