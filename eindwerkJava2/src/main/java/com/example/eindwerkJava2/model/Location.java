package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @OneToMany(mappedBy = "location")
    private List<Stock> stocks;

    @Column(name="locationName")
    private String locationName;

    @ManyToOne
    @JoinColumn(name="warehouse_Id")
    private Warehouse warehouse;

    @Column(name="singleStorage")
    private boolean singleStorage;

    @Column(name = "active")
    private int activeLocation = 1;


    public Location() {
    }



    public Location(Long locationId, List<Stock> stocks, String locationName, Warehouse warehouse, boolean singleStorage, int activeLocation) {

        this.locationId = locationId;
        this.stocks = stocks;
        this.locationName = locationName;
        this.warehouse = warehouse;
        this.singleStorage = singleStorage;
        this.activeLocation = activeLocation;
    }

    public int getActiveLocation() {
        return activeLocation;
    }

    public void setActiveLocation(int activeLocation) {
        this.activeLocation = activeLocation;
    }

    public Long getLocationId(){
        return locationId;
    }
    public void setLocationId(Long locationId) {
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

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
