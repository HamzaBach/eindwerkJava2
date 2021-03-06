package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Location repository.
 * @author Sebastiaan
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Find by location by it's id.
     *
     * @param locationId the location id
     * @return the location
     */
    Location findByLocationId(Long locationId);
    Location findByLocationNameAndActiveLocation(String locationName, int activeLocation);


    /**
     * Active locations list.
     *
     * @return the list with all active locations
     */
    @Query("SELECT u FROM Location u WHERE u.activeLocation = 1")
    List<Location> activeLocations();

    /**
     * Method to retrieve the maximum id of locations within the database.
     * @return The maximum used id within the location database is returned.
     */
    @Query(value = "select MAX(location_id) AS Max_Id from location",nativeQuery = true)
    Long getMaxId();

    @Query(value = "SELECT * from location WHERE active=1 AND location_type_id IN (SELECT location_type_id FROM location_type where single_storage = 0)", nativeQuery = true)
    List<Location> getNonSingleStorageLocations();

    @Query(value = "SELECT * from location WHERE active=1 AND location_type_id IN (SELECT location_type_id FROM location_type where single_storage = 1)", nativeQuery = true)
    List<Location> getSingleStorageLocations();

    @Query(value = "SELECT * from location WHERE active=1 AND warehouse_id =?1", nativeQuery = true)
    List<Location> findByWarehouse(long warehouseId);


}
