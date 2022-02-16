package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @Column(name="warehouseName")
    private String warehouseName;

    @OneToMany(mappedBy = "warehouse")
    private List<Location> locations;

    @Column(name = "active")
    private int activeWarehouse = 1;


    public int getActiveWarehouse() {
        return activeWarehouse;
    }
    public void setActiveWarehouse(int activeWarehouse) {
        this.activeWarehouse = activeWarehouse;
    }

    public Warehouse() {
    }

    public Warehouse(Long warehouseId, String warehouseName, List<Location> locations, int activeWarehouse) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.locations = locations;
        this.activeWarehouse = activeWarehouse;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}

