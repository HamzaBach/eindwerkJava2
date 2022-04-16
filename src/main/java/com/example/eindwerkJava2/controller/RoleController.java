package com.example.eindwerkJava2.controller;

import com.example.eindwerkJava2.service.RolesService;
import com.example.eindwerkJava2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The controller layer for connecting the front-end with the back-end for roles.
 * @author Hamza Bachiri
 * @version 1.0
 */
@Controller
public class RoleController {
    @Autowired
    private final RolesService rolesService;
    @Autowired
    private final UserService userService;


    public RoleController(RolesService rolesService, UserService userService){
        this.rolesService=rolesService;
        this.userService=userService;
    }

    //Get all roles
    @GetMapping("roles")
    public String findAll(Model model){
        model.addAttribute("roles",rolesService.getAllRoles());
        return "role";
    }

    //Unassign a role
    @GetMapping("unassignRole/{userId}/{roleId}")
    public String unassignRole(@PathVariable("userId") Long userId, @PathVariable Integer roleId){
        rolesService.unassignUserRole(userId,roleId);
        return "redirect:/edit/user/{userId}";
    }

    //Unassign a role
    @GetMapping("assignRole/{userId}/{roleId}")
    public String assignRole(@PathVariable("userId") Long userId, @PathVariable Integer roleId){
        rolesService.assignUserRole(userId,roleId);
        return "redirect:/edit/user/{userId}";
    }




}
