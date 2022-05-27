package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class LocationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationTypeId;
    private String name;
    private boolean singleStorage;
    private int active = 1;
    @Transient
    @OneToMany
    @JoinColumn(name = "locationId", nullable = true)
    private List<Location> locationsListPerType;

    public LocationType(String name, boolean singleStorage){
        this.setName(name);
        this.setSingleStorage(singleStorage);
    }
}
