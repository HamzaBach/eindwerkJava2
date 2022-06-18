package com.example.eindwerkJava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The model for the Warehouse.
 *
 * @author Sebastiaan
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    @Column(name = "warehouseName")
    private String warehouseName;

    @JsonBackReference(value = "warehouse-location")
    @OneToMany(mappedBy = "warehouse")
    private List<Location> locations;

    @Column(name = "active")
    private int activeWarehouse = 1;

    public Warehouse(String warehouseName){
        this.setWarehouseName(warehouseName);
    }

    @Column(name = "store")
    private boolean store;


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
     * Instantiates a new Warehouse.
     *
     * @param warehouseId     the warehouse id
     * @param warehouseName   the warehouse name
     * @param locations       link to the location table
     * @param activeWarehouse the active parameter for the warehouse
     * @param store           checked if the warehouse is the store
     */
    public Warehouse(Long warehouseId, String warehouseName, List<Location> locations, int activeWarehouse, boolean store) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.locations = locations;
        this.activeWarehouse = activeWarehouse;
        this.store = store;
    }

}

