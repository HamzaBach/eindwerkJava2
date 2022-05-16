package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.RoleRepository;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
import com.example.eindwerkJava2.wrappers.SuccessObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public SuccessEvaluator<User> getActiveUsers() {
        SuccessEvaluator<User> retrievedUsers = new SuccessEvaluator<>();
        List<User> activeUsers = this.userRepository.findByActiveUser(1);
        if (activeUsers.size() > 0) {
            retrievedUsers.setEntities(activeUsers);
            retrievedUsers.setIsSuccessfull(true);
        } else {
            retrievedUsers.setIsSuccessfull(false);
            retrievedUsers.setMessage("No active users found within the database.");
        }
        return retrievedUsers;
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
    public SuccessObject saveUser(User user, byte[] userImage) {
        SuccessObject success = new SuccessEvaluator<User>();
        Boolean existsUserName = userRepository.existsUserByUserName(user.getUserName());
        Boolean existsUserId = userRepository.existsUserByUserId(user.getUserId());
        if (existsUserName) {
            User duplicateUser = userRepository.findByUserName(user.getUserName());
            if (user.getUserId() == null && duplicateUser.getActiveUser() == 1) {
                success.setIsSuccessfull(false);
                success.setMessage("Cannot save this user because a user with user name " + user.getUserName() + " already exists!");
            } else if (user.getUserId() != null && user.getUserId() != duplicateUser.getUserId() && duplicateUser.getActiveUser() == 1) {
                success.setIsSuccessfull(false);
                success.setMessage("Cannot modify this user because a user with user name " + user.getUserName() + " already exists!");
            }
        } else {
            userImageHandler(user, userImage, existsUserId);
            SuccessObject passwordAndRolesHandler = passwordAndRolesHandler(user);
            if(passwordAndRolesHandler.getIsSuccessfull()){
                userRepository.save(user);
                success.setIsSuccessfull(true);
                success.setMessage("User " + user.getUserName() + " was successfully saved!");
            } else {
                success.setIsSuccessfull(false);
                success.setMessage(passwordAndRolesHandler.getMessage());
            }
        }
        return success;
    }

    private SuccessObject passwordAndRolesHandler(User user) {
        SuccessObject passwordSuccess = new SuccessEvaluator<>();
        if (user.getPassword().isEmpty()) {
            passwordSuccess.setMessage("Please input a password!");
            passwordSuccess.setIsSuccessfull(false);
            return passwordSuccess;
        } else {
            if (user.getUserId() == null) {
                user.addOneRole(roleRepository.findById(1).get());
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encryptedPassword);
            }
            if (userRepository.existsUserByUserName(user.getUserName())) {
                User user1 = userRepository.findByUserName(user.getUserName());
                if (user1.getUserId() == user.getUserId()) {
                    user.setRoles(userRepository.findById(user.getUserId()).get().getRoles());
                }
            }
            passwordSuccess.setIsSuccessfull(true);
            return passwordSuccess;
        }

    }

    private void userImageHandler(User user, byte[] userImage, Boolean existsUserId) {
        if (userImage.length == 0) {
            if (existsUserId) {
                User currentUser = userRepository.getById(user.getUserId());
                user.setUserImage(currentUser.getUserImage());
            }
        } else {
            user.setUserImage(userImage);
        }
    }

    /**
     * Method to find a user from the persistence layer.
     *
     * @param id The user id for obtaining the user object.
     * @return The user object is returned.
     */
    public SuccessEvaluator<User> findById(Long id) {
        SuccessEvaluator<User> success = new SuccessEvaluator<>();
        if (userRepository.existsUserByUserId(id)) {
            User user = userRepository.findById(id).get();
            success.setEntity(user);
            success.setIsSuccessfull(true);
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("User with Id "+id+" not found!");
        }
        return success;
    }

    /**
     * Method for deleting a user by setting the active user = 0.
     *
     * @param user The to be deleted user object.
     */
    public SuccessObject deleteUser(User user) {
        SuccessObject success = new SuccessEvaluator<>();
        user.setActiveUser(0);
        this.userRepository.save(user);
        if (userRepository.findById(user.getUserId()).get().getActiveUser() == 0) {
            success.setIsSuccessfull(true);
            success.setMessage("User " + user.getUserName() + " (ID: " + user.getUserId() + ")" + " was successfully removed.");
        } else {
            success.setIsSuccessfull(false);
            success.setMessage("User " + user.getUserName() + " (ID: " + user.getUserId() + ")" + " could not be removed.");
        }
        return success;
    }

}
