package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.EmployeeRole;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void saveUser(User user, byte[] userImage){
        if(userImage.length==0)
        {
            if(userRepository.existsUserByUserName(user.getUserName())){
                User currentUser = userRepository.getById(user.getUserId());
                user.setUserImage(currentUser.getUserImage());
            }
        }else{
            user.setUserImage(userImage);
        }
        userRepository.save(user);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public void deleteUser(User user){
        user.setActiveUser(0);
        this.userRepository.save(user);
    }

}
