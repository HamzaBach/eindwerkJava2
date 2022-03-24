package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.Article;
import com.example.eindwerkJava2.model.Role;
import com.example.eindwerkJava2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUserName(String userName);
    boolean existsUserByUserId(Long userId);
    User findByUserName(String userName);
    List<User> findByActiveUser(int activeUser);
}
