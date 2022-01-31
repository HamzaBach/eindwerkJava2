package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table(name="Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    @Column(name="locationName")
    private String locationName;

    @ManyToOne
    @JoinColumn(name="warehouse_Id")
    private Warehouse warehouse;

    @Column(name="singleStorage")
    private boolean singleStorage;

    public Location() {
    }

    public Location(int locationId, String locationName, Warehouse warehouse, boolean singleStorage) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.warehouse = warehouse;
        this.singleStorage = singleStorage;
    }

    public int getLocationId(){
        return locationId;
    }
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public boolean isSingleStorage() {
        return singleStorage;
    }

    public void setSingleStorage(boolean singleStorage) {
        this.singleStorage = singleStorage;
    }
}
