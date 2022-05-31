package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.LocationType;
import com.example.eindwerkJava2.model.Warehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LocationRepositoryTest {
    @Autowired
    private LocationTypeRepository locationTypeRepositoryTest;
    @Autowired
    private WarehouseRepository warehouseRepositoryTest;
    @Autowired
    private LocationRepository locationRepositoryTest;

    @BeforeEach
    void dummyData() {
        //Given+When:
        //// Defining location types:
        LocationType loadingDock = new LocationType("Loading dock", false);
        LocationType shoppingCart = new LocationType("Shopping cart", false);
        LocationType singleStorage = new LocationType("Single storage", true);
        locationTypeRepositoryTest.save(loadingDock);
        locationTypeRepositoryTest.save(shoppingCart);
        locationTypeRepositoryTest.save(singleStorage);
        //// Defining warehouses:
        Warehouse store = new Warehouse("Physical store");
        warehouseRepositoryTest.save(store);
        Warehouse storage = new Warehouse("Storage");
        warehouseRepositoryTest.save(storage);
        //// Defining single storage locations for store & storage:
        for (int i = 0; i < 10; i++) {
            Location location = new Location(store.getWarehouseName() + ": LOC " + i, store, singleStorage);
            locationRepositoryTest.save(location);
        }
        for (int i = 0; i < 10; i++) {
            Location location1 = new Location(storage.getWarehouseName() + ": LOC " + i, storage, singleStorage);
            locationRepositoryTest.save(location1);
        }
        ////Defining a loading dock for store & storage:
        Location storeLoadingDock = new Location(store.getWarehouseName() + ": DOCK 01", store, loadingDock);
        locationRepositoryTest.save(storeLoadingDock);
        Location storageLoadingDock = new Location(storage.getWarehouseName() + ": DOCK 01", storage, loadingDock);
        locationRepositoryTest.save(storageLoadingDock);
        Location storeShoppingCart = new Location(store.getWarehouseName() + ": CART 01", store, shoppingCart);
        locationRepositoryTest.save(storeShoppingCart);

        ////Defining one non-active location
        Location nonActiveLocation = new Location("notActiveLocation",store,singleStorage);
        nonActiveLocation.setActiveLocation(0);
        locationRepositoryTest.save(nonActiveLocation);
    }

    @Test
    void activeLocations() {
        //Then
        assertEquals(23,locationRepositoryTest.activeLocations().size());
    }

    @Test
    void getMaxId() {
        //Then
        assertEquals(24,locationRepositoryTest.getMaxId());
    }

    @Test
    void getNonSingleStorageLocations_Should_Give_Three() {
        //Then
        assertEquals(3,locationRepositoryTest.getNonSingleStorageLocations().size());
    }

    @Test
    void getSingleStorageLocations_Should_Give_20() {
        //Then
        assertEquals(20, locationRepositoryTest.getSingleStorageLocations().size());
    }

    @AfterEach
    void tearDown() {
        locationRepositoryTest.deleteAll();
        locationTypeRepositoryTest.deleteAll();
        warehouseRepositoryTest.deleteAll();
    }
}