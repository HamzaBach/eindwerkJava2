package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.EmployeeRole;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Boolean addUser(String userName, String password, EmployeeRole employeeRole) {
        if (!userRepository.existsUserByUserName(userName)) {
            User newUser = new User(userName, password, employeeRole);
            userRepository.save(newUser);
            return true;
        } else return false;
    }

    public User getUserByUserName(String userName) {
        if (userRepository.existsUserByUserName(userName)) {
            User retrievedUser = userRepository.findByUserName(userName);
            return retrievedUser;
        } else throw new IllegalStateException("User with username: "+userName+" could not been found.");
    }
}
