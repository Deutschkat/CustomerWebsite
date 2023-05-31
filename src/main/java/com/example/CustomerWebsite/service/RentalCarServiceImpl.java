package com.example.CustomerWebsite.service;

import com.example.CustomerWebsite.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalCarServiceImpl implements RentalCarService {

    @Autowired
    final RentalCarRepository rentalCarRepository;
    final CustomerRepository customerRepository;

    @Override
    public RentalCar getRentalCarById(Long id){
        return rentalCarRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No rental car with the ID : " + id));

    }

    @Override
    @Transactional

    public RentalCar saveRentalCar(RentalCar rentalCar) {
        if (rentalCar == null) {
            throw new IllegalArgumentException("Rental car must not be empty");
        }
        return rentalCarRepository.save(rentalCar);
    }

    @Override
    @Transactional
    public void deleteRentalCar(Long id) {
        if (!rentalCarRepository.existsById(id)) {
            throw new NoSuchElementException("No rental car with the ID number: " + id);
        }
        rentalCarRepository.deleteById(id);
    }


    @Override
    public List<RentalCar> getAllRentalCars() {
        return rentalCarRepository.findAll();
    }

    @Override
    @Transactional
    public void rentCar(Long carId, Long customerId) {
        RentalCar car = rentalCarRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!car.isAvailable()) {
            throw new RuntimeException("Car is not available");
        }

        car.setAvailable(false);
        car.setCustomer(customer);
        rentalCarRepository.save(car);
    }

    @Override
    @Transactional
    public void returnCar(Long carId) {
        RentalCar car = rentalCarRepository.findById(carId)
                .orElseThrow(() -> new NoSuchElementException("Car not found"));

        if (car.isAvailable()) {
            throw new RuntimeException("Car is not currently rented");
        }

        Customer customer = car.getCustomer();
        if (customer != null) {
            customer.setRentalCar(null);
            customerRepository.save(customer);
        }

        car.setAvailable(true);
        car.setCustomer(null);
        rentalCarRepository.save(car);
    }

    @Override
    public List<RentalCar> getAllAvailableCars() {
        List<RentalCar> allCars = rentalCarRepository.findAll();
        List<RentalCar> availableCars = new ArrayList<>();

        for (RentalCar car : allCars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }

        return availableCars;
    }



}
