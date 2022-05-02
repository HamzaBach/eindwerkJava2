package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.SaleHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleHeaderRepository extends JpaRepository<SaleHeader, Long> {

}
