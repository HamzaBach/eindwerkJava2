package com.example.eindwerkJava2.repositories;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Warehouse findByWarehouseId(Long locationId);

    List<Warehouse> findByActiveWarehouse(int activeWarehouse);

}
