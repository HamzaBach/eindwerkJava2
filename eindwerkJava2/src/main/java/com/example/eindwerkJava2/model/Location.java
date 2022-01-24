package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table(name="Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    @Column(name="Location")
    private String locationName;

    @ManyToOne
    @JoinColumn(name="warehouseId")
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
    public String getLocation() {
        return locationName;
    }
    public void setLocation(String locationName) {
        this.locationName = locationName;
    }
}
