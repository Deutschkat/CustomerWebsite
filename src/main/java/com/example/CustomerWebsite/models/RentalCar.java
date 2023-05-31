package com.example.CustomerWebsite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "rental_cars")
@Getter
@Setter
public class RentalCar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String model;
    private String color;
    private String make;
    private int year;
    private String transmission;
    private boolean available;

    @OneToOne
    private Customer customer;

}
