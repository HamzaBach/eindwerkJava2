package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import com.example.eindwerkJava2.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Warehouse service.
 * @author Sebastiaan
 */
@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;


    /**
     * Instantiates a new Warehouse service.
     *
     * @param WarehouseRepository the warehouse repository
     */
    @Autowired
    public WarehouseService(WarehouseRepository WarehouseRepository) {
        this.warehouseRepository = WarehouseRepository;
    }

    /**
     * Gets all warehouses.
     *
     * @return the all warehouses
     */
    public List<Warehouse> getAllWarehouses() {
        return this.warehouseRepository.findAll();
    }

    /**
     * Add a warehouse.
     *
     * @param warehouse the warehouse
     */
    public void addWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    /**
     * Find warehouse warehouse.
     *
     * @param warehouseId the warehouse id
     * @return the warehouse
     * @throws Exception the exception occurs when the ID does not exist
     */
    public Warehouse findWarehouse(Long warehouseId) throws Exception {
        if(warehouseRepository.existsById(warehouseId)){
        return this.warehouseRepository.findByWarehouseId(warehouseId);
    } else{
           throw new IllegalStateException("Deze ID bestaat niet");
        }
    }

    /**
     * Delete a warehouse.
     *
     * @param warehouse the warehouse
     */
    public void deleteWarehouse(Warehouse warehouse){
        warehouse.setActiveWarehouse(0);
        this.warehouseRepository.save(warehouse);
    }

    /**
     * List of all active warehouses.
     *
     * @return the list
     */
    public List<Warehouse> activeWarehouses(){
        return this.warehouseRepository.findByActiveWarehouse(1);
    }





}
