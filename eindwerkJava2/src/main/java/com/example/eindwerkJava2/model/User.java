package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a user object.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Getter
@Setter
@EqualsAndHashCode
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
     * Setter method to set only 1 role.
     *
     * @param role The to be set role to the user object.
     */
    public void addOneRole(Role role) {
        this.roles.add(role);
    }
}
