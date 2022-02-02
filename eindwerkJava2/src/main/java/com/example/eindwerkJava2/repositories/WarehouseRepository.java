package com.example.eindwerkJava2.repositories;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {


    List<Warehouse> findByWarehouseId(int warehouseId);

}
