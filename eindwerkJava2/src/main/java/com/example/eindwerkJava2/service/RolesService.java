package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.RoleRepository;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.wrappers.ArticleSuccess;
import com.example.eindwerkJava2.wrappers.RolesSuccess;
import com.example.eindwerkJava2.wrappers.SuccessObject;
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

    public RolesSuccess getAllRoles() {
        RolesSuccess retrievedRoles = new RolesSuccess();
        List<Role> activeRoles = this.roleRepository.findAll();
        if (activeRoles.size() > 0) {
            retrievedRoles.setRoles(activeRoles);
            retrievedRoles.setIsSuccessfull(true);
        } else {
            retrievedRoles.setIsSuccessfull(false);
            retrievedRoles.setMessage("No roles found within the database.");
        }
        return retrievedRoles;
    }

    //Get Role by Id
    public RolesSuccess findRoleById(Integer id){
        RolesSuccess success = new RolesSuccess();
        if(roleRepository.findById(id).isEmpty()){
            success.setIsSuccessfull(false);
            success.setMessage("Role not found!");
        } else {
            Role role = roleRepository.findById(id).get();
            success.setRole(role);
            success.setIsSuccessfull(true);
        }
        return success;
    }

    //Delete Role
    public SuccessObject deleteRole(Integer id){
        RolesSuccess findRoleSuccess = findRoleById(id);
        SuccessObject deleteRoleSuccess = new RolesSuccess();
        if(findRoleSuccess.getIsSuccessfull()){
            Role toBeDeletedRole = roleRepository.findById(id).get();
            roleRepository.deleteById(id);
            deleteRoleSuccess.setIsSuccessfull(true);
            deleteRoleSuccess.setMessage("Role "+toBeDeletedRole.getName()+" has been successfully removed!");
        } else {
            deleteRoleSuccess.setIsSuccessfull(false);
            deleteRoleSuccess.setMessage("Role with id: "+id+ "could not been removed!");
        }
        return deleteRoleSuccess;
    }

    //Update Role
    public SuccessObject save (Role role){
        SuccessObject isSaveSuccessfull = new RolesSuccess();
        Boolean existsRoleByName = roleRepository.existsRoleByName(role.getName());
        if(existsRoleByName){
            Role roleWithSameName = roleRepository.findByName(role.getName());
            if(role.getId()==null){
                isSaveSuccessfull.setIsSuccessfull(false);
                isSaveSuccessfull.setMessage("New role cannot be added because this role name "+role.getName()+" already exists!");
                return isSaveSuccessfull;
            }
            if(role.getId()!=null && roleWithSameName.getId()!=role.getId()){
                isSaveSuccessfull.setIsSuccessfull(false);
                isSaveSuccessfull.setMessage("Cannot modify this role because the role name "+role.getName()+" already exists!");
                return isSaveSuccessfull;
            }
        }
        roleRepository.save(role);
        return isSaveSuccessfull;
    }

    //Assign a role
    public Boolean assignUserRole(Long userId, Integer roleId){
        if(userId==null){
            return false;
        } else {
            User user = userRepository.findById(userId).orElse(null);
            Role role = roleRepository.findById(roleId).orElse(null);

            Set<Role> userRoles = user.getRoles();
            userRoles.add(role);
            user.setRoles(userRoles);

            userRepository.save(user);
            return true;
        }

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
