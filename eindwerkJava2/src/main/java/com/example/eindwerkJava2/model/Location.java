package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * This is the model for locations.
 *
 * @author Sebastiaan
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Location")
public class Location {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @OneToMany(mappedBy = "location")
    private List<Stock> stocks;

    @Column(name = "locationName")
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "warehouse_Id")
    private Warehouse warehouse;

    @Column(name = "singleStorage")
    private boolean singleStorage;

    @Column(name = "active")
    private int activeLocation = 1;


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

}
