package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "States")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stateId;

    @Column(name = "state_name")
    private String stateName;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;
}
