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
    private int activeUser=1;
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] userImage;

    public User(){}

    public User(String userName, String password, EmployeeRole role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public int getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(int activeUser) {
        this.activeUser = activeUser;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }
}
