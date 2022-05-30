package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepositoryTest;
    @Autowired
    UserRepository userRepositoryTest;

    @BeforeEach
    void addDefaultRolesAndUsers(){
        List<Role> defaultRoles = new ArrayList<Role>();
        Role role1 = new Role("USER");
        Role role2 = new Role("CREATOR");
        Role role3 = new Role("EDITOR");
        Role role4 = new Role("ADMIN");
        defaultRoles.add(role1);
        defaultRoles.add(role2);
        defaultRoles.add(role3);
        defaultRoles.add(role4);
        for(Role role:defaultRoles){
            if(!roleRepositoryTest.existsRoleByName(role.getName())){
                roleRepositoryTest.save(role);
            }
        }

        User user1 = new User("Hamza","ww");
        User user2 = new User("Rinaldo","Teddybeer");
        user1.addOneRole(role1);
        user2.addOneRole(role2);
        user2.addOneRole(role3);
        user2.addOneRole(role4);
        userRepositoryTest.save(user1);
        userRepositoryTest.save(user2);

    }

    @AfterEach
    void tearDown() {
        roleRepositoryTest.deleteAll();
        userRepositoryTest.deleteAll();
    }

    @Test
    void getUserNotRoles_Should_Give_Three_And_One() {
        assertEquals(3, roleRepositoryTest.getUserNotRoles(userRepositoryTest.findByUserName("Hamza").getUserId()).size());
        assertEquals(1, roleRepositoryTest.getUserNotRoles(userRepositoryTest.findByUserName("Rinaldo").getUserId()).size());
    }
}