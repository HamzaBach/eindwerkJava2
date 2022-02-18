package com.example.eindwerkJava2.repositories;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * The interface Warehouse repository.
 * @author Sebastiaan
 */
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    /**
     * Find warehouse by id.
     *
     * @param locationId the location id
     * @return the warehouse
     */
    Warehouse findByWarehouseId(Long locationId);

    /**
     * Find list of all active warehouses.
     *
     * @param activeWarehouse the active warehouse
     * @return the list of active warehouses
     */
    List<Warehouse> findByActiveWarehouse(int activeWarehouse);

}
