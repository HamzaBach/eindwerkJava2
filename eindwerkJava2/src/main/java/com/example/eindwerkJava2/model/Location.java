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

    @ManyToOne
    @JoinColumn(name = "locationTypeId")
    private LocationType locationType;

    @Column(name = "active")
    private int activeLocation = 1;

    

    public Location(String locationName, Warehouse warehouse, LocationType locationType){
        this.setLocationName(locationName);
        this.setWarehouse(warehouse);
        this.setLocationType(locationType);
    }


}
