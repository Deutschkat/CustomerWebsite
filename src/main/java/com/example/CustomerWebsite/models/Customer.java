package com.example.CustomerWebsite.models;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Builder
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String emailAddress;
    private Integer age;
    private String address;
    private Long carId;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private RentalCar rentalCar;


}
