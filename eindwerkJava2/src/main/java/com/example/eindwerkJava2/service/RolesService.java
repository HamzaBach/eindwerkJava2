package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private final RoleRepository roleRepository;

    public RolesService(RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

}
