package com.example.eindwerkJava2.model;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String password;
    private EmployeeRole role;

    public User(){}

    public User(String userName, String password, EmployeeRole role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
