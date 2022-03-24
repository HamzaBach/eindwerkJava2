package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.RoleRepository;
import com.example.eindwerkJava2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RolesService {
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;

    public RolesService(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    //Get Role by Id
    public Optional<Role> findRoleById(Integer id){
        return roleRepository.findById(id);
    }

    //Delete Role
    public void deleteRole(Integer id){
        roleRepository.deleteById(id);
    }

    //Update Role
    public void save (Role role){
        roleRepository.save(role);
    }

    //Assign a role
    public void assignUserRole(Long userId, Integer roleId){
        User user = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);

        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    //Unassign a role
    public void unassignUserRole(Long userId, Integer roleId){
        User user = userRepository.findById(userId).orElse(null);
        Set<Role> userRoles = user.getRoles();

        userRoles.removeIf(x -> x.getId()==roleId);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    //Get roles of a user
    public Set<Role> getUserRoles(User user){
        return user.getRoles();
    }

    //Get roles which the user does not have
    public Set<Role> getUserNotRoles(User user){
        return roleRepository.getUserNotRoles(user.getUserId());

    }

}
