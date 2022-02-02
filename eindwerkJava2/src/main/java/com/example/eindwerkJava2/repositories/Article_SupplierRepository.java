package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article_Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

public interface Article_SupplierRepository extends JpaRepository<Article_Supplier, Long> {

}
