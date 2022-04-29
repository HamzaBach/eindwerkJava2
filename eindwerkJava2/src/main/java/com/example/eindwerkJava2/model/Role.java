package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * This class represents a role that is used in the Spring Security framework.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
@Getter
@Setter
@EqualsAndHashCode
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

}
