package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Persistence layer for interacting with user objects from the database.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Method to verify if a user exists by a particular user name.
     *
     * @param userName The to be verified user name.
     * @return A boolean is returned depending if the user name exists or not.
     */
    boolean existsUserByUserName(String userName);

    /**
     * Method to verify if a user exists by a particular user id.
     *
     * @param userId The to be verified user id.
     * @return A boolean is returned depending if the user id exists or not.
     */
    boolean existsUserByUserId(Long userId);

    /**
     * Method to retrieve a user object by a user name.
     *
     * @param userName The user name to retrieve the user object.
     * @return The user object based on the inputted user name.
     */
    User findByUserName(String userName);

    /**
     * Method to obtain all users with a particular activeUer value.
     *
     * @param activeUser activeUser value 1 (active) or 0(inactive).
     * @return A list of users is returned that have the same activeUser value.
     */
    List<User> findByActiveUser(int activeUser);


}
