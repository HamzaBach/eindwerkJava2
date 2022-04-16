package com.example.eindwerkJava2.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a user object.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String userName;
    private String password;
    private int activeUser = 1;
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    private byte[] userImage;

    //Table "users_roles" in between users & roles to enable the many to many relationship
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * An empty constructor to create a new user object.
     */
    public User() {
    }

    /**
     * A constructor to create a new user object.
     *
     * @param userName The username of the user object.
     * @param password The password of the user object.
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * A constructor to create a new user object.
     *
     * @param userName The username of the user object.
     * @param password The password of the user object.
     * @param roles    A set of roles attributed to the user object.
     */
    public User(String userName, String password, Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    /**
     * Getter method for obtaining the user id.
     *
     * @return The user id of the object is returned.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Setter method for setting the user id.
     *
     * @param userId The to be set user id.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Getter method for obtaining the user name.
     *
     * @return The user name of the object is returned.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for setting the user name.
     *
     * @param userName The to be set user name.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for obtaining the password.
     *
     * @return The password of the object is returned.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for setting the password.
     *
     * @param password The to be set password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for obtaining whether the user is active (1) or not (0).
     *
     * @return The value of active user is returned.
     */
    public int getActiveUser() {
        return activeUser;
    }

    /**
     * Setter method for setting the active user value.
     *
     * @param activeUser The to be set active user value.
     */
    public void setActiveUser(int activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Getter method for obtaining the user image.
     *
     * @return The user image of the object is returned.
     */
    public byte[] getUserImage() {
        return userImage;
    }

    /**
     * Setter method for setting the user's image.
     *
     * @param userImage The to be set user image.
     */
    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    /**
     * Getter method for obtaining the assigned roles of the user.
     *
     * @return The set of roles of the user is returned.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Setter method for setting the roles for the user object.
     *
     * @param roles The set of roles to be attributed to the user.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Setter method to set only 1 role.
     *
     * @param role The to be set role to the user object.
     */
    public void addOneRole(Role role) {
        this.roles.add(role);
    }
}
