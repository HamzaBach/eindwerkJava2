package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.util.List;

/**
 * This is the model for locations.
 *
 * @author Sebastiaan
 */
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


    /**
     * Instantiates a new Location.
     */
    public Location() {
    }


    /**
     * Instantiates a new Location.
     *
     * @param locationId     ID of the location in the database
     * @param stocks         link to stock table
     * @param locationName   name of the location
     * @param warehouse      the warehouse that the location belongs to
     * @param singleStorage  wether the location is single storage or not
     * @param activeLocation location ( active or inactive(=deleted) )
     */
    public Location(Long locationId, List<Stock> stocks, String locationName, Warehouse warehouse, boolean singleStorage, int activeLocation) {

        this.locationId = locationId;
        this.stocks = stocks;
        this.locationName = locationName;
        this.warehouse = warehouse;
        this.singleStorage = singleStorage;
        this.activeLocation = activeLocation;
    }

    /**
     * Gets active location.
     *
     * @return activeLocation where the location is active(1) or not (0)
     */
    public int getActiveLocation() {
        return activeLocation;
    }

    /**
     * Sets active location.
     *
     * @param activeLocation sets the location to active or inactive
     */
    public void setActiveLocation(int activeLocation) {
        this.activeLocation = activeLocation;
    }

    /**
     * Get location id long.
     *
     * @return the locationId
     */
    public Long getLocationId(){
        return locationId;
    }

    /**
     * Sets location id.
     *
     * @param locationId the location id
     */
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    /**
     * Gets location name.
     *
     * @return the location name
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets location name.
     *
     * @param locationName the location name
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Gets warehouse.
     *
     * @return the warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Sets warehouse.
     *
     * @param warehouse the warehouse
     */
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Is single storage boolean.
     *
     * @return the boolean
     */
    public boolean isSingleStorage() {
        return singleStorage;
    }

    /**
     * Sets single storage.
     *
     * @param singleStorage the single storage
     */
    public void setSingleStorage(boolean singleStorage) {
        this.singleStorage = singleStorage;
    }

    /**
     * Gets stocks.
     *
     * @return the stocks
     */
    public List<Stock> getStocks() {
        return stocks;
    }

    /**
     * Sets stocks.
     *
     * @param stocks the stocks
     */
    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
