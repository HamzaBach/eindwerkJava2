package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query(value = "SELECT * FROM supplier WHERE active = 1", nativeQuery = true)
    List<Supplier> findAllActiveUsers();

}