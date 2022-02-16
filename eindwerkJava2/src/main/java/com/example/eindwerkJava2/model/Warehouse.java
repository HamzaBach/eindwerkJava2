package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.util.List;

/**
 * The mmodel for the Warehouse.
 * @author Sebastiaan
 */
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


    /**
     * Gets active warehouse.
     *
     * @return the activeState of the warehouse
     */
    public int getActiveWarehouse() {
        return activeWarehouse;
    }

    /**
     * Sets the warehouse to active/inactive.
     *
     * @param activeWarehouse the active parameter for the warehouse
     */
    public void setActiveWarehouse(int activeWarehouse) {
        this.activeWarehouse = activeWarehouse;
    }

    /**
     * Instantiates a new Warehouse. Empty constructor
     */
    public Warehouse() {
    }

    /**
     * Instantiates a new Warehouse.
     *
     * @param warehouseId     the warehouse id
     * @param warehouseName   the warehouse name
     * @param locations       link to the location table
     * @param activeWarehouse the active parameter for the warehouse
     */
    public Warehouse(Long warehouseId, String warehouseName, List<Location> locations, int activeWarehouse) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.locations = locations;
        this.activeWarehouse = activeWarehouse;
    }

    /**
     * Gets warehouse id.
     *
     * @return the warehouse id
     */
    public Long getWarehouseId() {
        return warehouseId;
    }

    /**
     * Sets warehouse id.
     *
     * @param warehouseId the warehouse id
     */
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * Gets warehouse name.
     *
     * @return the warehouse name
     */
    public String getWarehouseName() {
        return warehouseName;
    }

    /**
     * Sets warehouse name.
     *
     * @param warehouseName the warehouse name
     */
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    /**
     * Gets locations.
     *
     * @return a List of all locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Sets locations.
     *
     * @param locations the locations
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}

