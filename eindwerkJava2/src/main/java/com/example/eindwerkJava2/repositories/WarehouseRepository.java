package com.example.eindwerkJava2.repositories;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Warehouse findByWarehouseId(Long locationId);

    @Query("SELECT u FROM Warehouse u WHERE u.activeWarehouse = 1")
    List<Warehouse> activeWarehouses();

}
