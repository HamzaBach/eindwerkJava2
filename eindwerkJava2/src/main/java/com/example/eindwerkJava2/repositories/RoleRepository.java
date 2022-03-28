package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsRoleByName(String roleName);
    @Query(
            value = "SELECT * FROM roles WHERE role_id NOT IN (SELECT role_id FROM users_roles WHERE user_id = ?1)",
            nativeQuery = true
    )
    Set<Role> getUserNotRoles(Long userId);

    Role findByName(String roleName);

}
