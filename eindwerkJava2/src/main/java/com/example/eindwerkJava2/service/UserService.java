package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.RoleRepository;
import com.example.eindwerkJava2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class represents the service layer for users.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Service
public class UserService {
    /**
     * Autowiring of the UserRepository from the IOC container.
     */
    @Autowired
    private final UserRepository userRepository;
    /**
     * Autowiring of the RoleRepository from the IOC container.
     */
    @Autowired
    private final RoleRepository roleRepository;
    /**
     * Autorwiring of the BcryptPasswordEncoder from the IOC container.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final Role DEFAULT_ROLE = new Role("USER");

    /**
     * Dependency injection of the UserRepository and RoleRepository into the UserService.
     *
     * @param userRepository The to be injected UserRepository.
     * @param roleRepository The to be injected RoleRepository.
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Getter method for getting the active user value.
     *
     * @return The active user value is returned.
     */
    public List<User> getActiveUsers() {
        return this.userRepository.findByActiveUser(1);
    }

    /**
     * Getter method for getting the user name.
     *
     * @param userName The to be retrieved user name.
     * @return The retrieved user object is returned.
     */
    public User getUserByUserName(String userName) {
        if (userRepository.existsUserByUserName(userName)) {
            User retrievedUser = userRepository.findByUserName(userName);
            return retrievedUser;
        } else throw new IllegalStateException("User with username: " + userName + " could not been found.");
    }

    /**
     * Method for saving or updating a user.
     *
     * @param user      The to be saved user.
     * @param userImage The image of the user that is saved within the user object.
     */
    public void saveUser(User user, byte[] userImage) {
        if (userImage.length == 0) {
            if (userRepository.existsUserByUserId(user.getUserId())) {
                User currentUser = userRepository.getById(user.getUserId());
                user.setUserImage(currentUser.getUserImage());
            }
        } else {
            user.setUserImage(userImage);
        }

        if (user.getUserId() == null) {
            user.addOneRole(roleRepository.findById(1).get());
            if(user.getPassword()==null){
                User user1=userRepository.findByUserName(user.getUserName());
                user.setPassword(user1.getPassword());
            }
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        } else {
            user.setRoles(userRepository.findById(user.getUserId()).get().getRoles());
        }
        //ToDo: make username unique, if a new user is added with existing user name we should block it.
        if (!userRepository.existsUserByUserName(user.getUserName())){
            userRepository.save(user);
        }

    }

    /**
     * Method to find a user from the persistence layer.
     *
     * @param id The user id for obtaining the user object.
     * @return The user object is returned.
     */
    public Optional<User> findById(Long id) {
        if (userRepository.existsUserByUserId(id)) {
            return userRepository.findById(id);
        } else {
            return null;
        }
    }

    /**
     * Method for deleting a user by setting the active user = 0.
     *
     * @param user The to be deleted user object.
     */
    public void deleteUser(User user) {
        user.setActiveUser(0);
        this.userRepository.save(user);
    }

}
