package com.example.CustomerWebsite.models;

import java.util.List;

public interface RentalCarService {
    RentalCar getRentalCarById(Long id);

    RentalCar saveRentalCar(RentalCar rentalCar);

    void deleteRentalCar(Long id);

    List<RentalCar> getAllRentalCars();

    void rentCar(Long carId, Long customerId);

    void returnCar(Long carId);

    List<RentalCar> getAllAvailableCars();
}
