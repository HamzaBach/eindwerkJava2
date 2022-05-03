package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.SaleLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleLineRepository extends JpaRepository<SaleLine, Long> {
}
