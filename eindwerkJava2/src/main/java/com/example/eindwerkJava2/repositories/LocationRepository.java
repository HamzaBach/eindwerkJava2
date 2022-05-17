package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Location;
import com.example.eindwerkJava2.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Location repository.
 * @author Sebastiaan
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Find by location by it's id.
     *
     * @param locationId the location id
     * @return the location
     */
    Location findByLocationId(Long locationId);


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


}
