package com.example.eindwerkJava2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
public class Vat {
    @Id
    @GeneratedValue
            (strategy = GenerationType.IDENTITY)
    private long vatId;
    private double vatRate;

    private int active = 1;

    public Vat(double vatRate){
        this.vatRate=vatRate;
    }
}
