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

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final Role DEFAULT_ROLE=new Role("USER");

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getActiveUsers() {
        return this.userRepository.findByActiveUser(1);
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

        if(user.getUserId()==null){
            user.addOneRole(roleRepository.findById(1).get());
            String encryptedPassword=passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
        }else{
            user.setRoles(userRepository.findById(user.getUserId()).get().getRoles());
        }
        userRepository.save(user);
    }

    public Optional<User> findById(Long id){
        if(userRepository.existsUserByUserId(id)){
            return userRepository.findById(id);
        }else{
            return null;
        }
    }

    public void deleteUser(User user){
        user.setActiveUser(0);
        this.userRepository.save(user);
    }

}
