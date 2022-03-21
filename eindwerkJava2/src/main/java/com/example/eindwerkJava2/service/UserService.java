package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.EmployeeRole;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.tools.AESEncryptionImpl;
import com.example.eindwerkJava2.tools.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    private Encryption aesEncryption = new AESEncryptionImpl();
    private String encryptedPassword;
    private final String secretKey = "EindprojectJavaJaar2";


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getActiveUsers() {
        return this.userRepository.findByActiveUser(1);
    }

    public Boolean addUser(String userName, String password, EmployeeRole employeeRole) {
        if (!userRepository.existsUserByUserName(userName)) {
            User newUser = new User(userName, aesEncryption.encrypt(password,"EindprojectJavaJaar2"), employeeRole);
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
            if(userRepository.existsUserByUserId(user.getUserId())){
                User currentUser = userRepository.getById(user.getUserId());
                user.setUserImage(currentUser.getUserImage());
            }
        }else{
            user.setUserImage(userImage);
        }
        encryptedPassword=aesEncryption.encrypt(user.getPassword(),secretKey);
        user.setPassword(encryptedPassword);
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
