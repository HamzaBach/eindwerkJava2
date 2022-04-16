package com.example.eindwerkJava2.model;

import javax.persistence.*;

/**
 * This class represents a role that is used in the Spring Security framework.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    /**
     * Constructor for creating a role object without any parameters.
     */
    public Role() {
    }

    /**
     * Constructor for creating a role object with a role name.
     *
     * @param name The name of the role.
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Getter method for obtaining the id of the role.
     *
     * @return The role id is returned.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter method for setting the id of the role.
     *
     * @param id The to be set id for the role object.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter method for getting the name of the role.
     *
     * @return The name of the role is returned.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for setting the name of the role.
     *
     * @param name The to be set name of the role.
     */
    public void setName(String name) {
        this.name = name;
    }
}
