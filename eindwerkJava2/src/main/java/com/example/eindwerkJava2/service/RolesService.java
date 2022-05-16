package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import com.example.eindwerkJava2.repositories.RoleRepository;
import com.example.eindwerkJava2.repositories.UserRepository;
import com.example.eindwerkJava2.wrappers.SuccessEvaluator;
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

    public SuccessEvaluator<Role> getAllRoles() {
        SuccessEvaluator<Role> retrievedRoles = new SuccessEvaluator<>();
        List<Role> activeRoles = this.roleRepository.findAll();
        if (activeRoles.size() > 0) {
            retrievedRoles.setEntities(activeRoles);
            retrievedRoles.setIsSuccessfull(true);
        } else {
            retrievedRoles.setIsSuccessfull(false);
            retrievedRoles.setMessage("No roles found within the database.");
        }
        return retrievedRoles;
    }

    //Get Role by Id
    public SuccessEvaluator<Role> findRoleById(Integer id){
        SuccessEvaluator<Role> success = new SuccessEvaluator<>();
        if(roleRepository.findById(id).isEmpty()){
            success.setIsSuccessfull(false);
            success.setMessage("Role not found!");
        } else {
            Role role = roleRepository.findById(id).get();
            success.setEntity(role);
            success.setIsSuccessfull(true);
        }
        return success;
    }

    //Delete Role
    public SuccessObject deleteRole(Integer id){
        SuccessEvaluator<Role> findRoleSuccess = findRoleById(id);
        SuccessObject deleteRoleSuccess = new SuccessEvaluator<>();
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
        SuccessObject isSaveSuccessfull = new SuccessEvaluator<Role>();
        boolean existsRoleByName = roleRepository.existsRoleByName(role.getName());
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
    public SuccessObject assignUserRole(Long userId, Integer roleId){
        SuccessObject assignUserRoleSuccess = new SuccessEvaluator<Role>();
        boolean existsRoleById = roleRepository.existsById(roleId);
        if(existsRoleById){
            Role toBeAssignedRole = roleRepository.findById(roleId).get();
            if(userId==null){
                assignUserRoleSuccess.setIsSuccessfull(false);
                assignUserRoleSuccess.setMessage("The role "+toBeAssignedRole.getName()+ " could not be added because the user id is null!");
            } else {
                boolean existsUserById = userRepository.existsById(userId);
                if(existsUserById){
                    User user = userRepository.findById(userId).orElse(null);
                    Role role = roleRepository.findById(roleId).orElse(null);
                    Set<Role> userRoles = user.getRoles();
                    userRoles.add(role);
                    user.setRoles(userRoles);
                    userRepository.save(user);
                    assignUserRoleSuccess.setIsSuccessfull(true);
                    assignUserRoleSuccess.setMessage("The role "+toBeAssignedRole.getName()+" has been successfully assigned to "+user.getUserName()+".");
                } else {
                    assignUserRoleSuccess.setIsSuccessfull(false);
                    assignUserRoleSuccess.setMessage("The user with id "+userId+" could not be found in the database!");
                }
            }
        }else {
            assignUserRoleSuccess.setIsSuccessfull(false);
            assignUserRoleSuccess.setMessage("No such role with role id "+roleId+" could be found in the database!");
        }
        return assignUserRoleSuccess;
    }

    //Unassign a role
    public SuccessObject unassignUserRole(Long userId, Integer roleId){
        SuccessObject unassignUserRoleSuccess = new SuccessEvaluator<>();
        boolean existsRoleById = roleRepository.existsById(roleId);
        if(existsRoleById){
            Role toBeUnassignedRole = roleRepository.findById(roleId).get();
            if(userId==null){
                unassignUserRoleSuccess.setIsSuccessfull(false);
                unassignUserRoleSuccess.setMessage("The role "+toBeUnassignedRole.getName()+ " could not be removed because the user id is null!");
            } else {
                boolean existsUserById = userRepository.existsById(userId);
                if(existsUserById){
                    User user = userRepository.findById(userId).orElse(null);
                    Set<Role> userRoles = user.getRoles();

                    userRoles.removeIf(x -> x.getId()==roleId);
                    user.setRoles(userRoles);
                    userRepository.save(user);
                    unassignUserRoleSuccess.setIsSuccessfull(true);
                    unassignUserRoleSuccess.setMessage("The role "+toBeUnassignedRole.getName()+" has been successfully removed from "+user.getUserName()+".");
                } else {
                    unassignUserRoleSuccess.setIsSuccessfull(false);
                    unassignUserRoleSuccess.setMessage("The user with id "+userId+" could not be found in the database!");
                }
            }
        }else {
            unassignUserRoleSuccess.setIsSuccessfull(false);
            unassignUserRoleSuccess.setMessage("No such role with role id "+roleId+" could be found in the database!");
        }
        return unassignUserRoleSuccess;
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
