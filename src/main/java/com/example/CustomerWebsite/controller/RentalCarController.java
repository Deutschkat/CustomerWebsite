package com.example.CustomerWebsite.controller;

import com.example.CustomerWebsite.models.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class RentalCarController {

    @Autowired
    private final RentalCarService rentalCarService;
    private final CustomerService customerService;

    @GetMapping("/cars")
    public String viewCarList(Model model) {
        List<RentalCar> rentalCars = rentalCarService.getAllRentalCars();
        model.addAttribute("rentalCars", rentalCars);
        return "rental-cars";
    }

    @GetMapping("/new_car")
    public String showNewCarPage(Model model){
        RentalCar rentalCar = new RentalCar();
        model.addAttribute("rentalCar", rentalCar);
        return "new-rental-car";

    }

    @PostMapping(value = "/save_car")
    public String saveRentalCar(@ModelAttribute("rentalCar") RentalCar rentalCar, Model model){
        try {
            rentalCarService.saveRentalCar(rentalCar);
            return "redirect:/cars";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/car/{id}")
    public String viewCarDetails(@PathVariable Long id, Model model) {
        RentalCar car = rentalCarService.getRentalCarById(id);
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("car", car);
        model.addAttribute("customers", customers);
        return "rental-car";
    }


    @GetMapping("/rental/{id}")
    public ModelAndView showEditCustomerPage(@PathVariable(name = "id") Long id, Model model){
        try {
            ModelAndView mav = new ModelAndView("rental-page");
            Customer customer = customerService.getCustomer(id);
            List<RentalCar> availableCars = rentalCarService.getAllAvailableCars();

            model.addAttribute("customer", customer);
            model.addAttribute("availableCars", availableCars);

            if (customer == null) {
                throw new IllegalArgumentException("No customer with the ID number of: " + id);
            }
            mav.addObject("customer", customer);
            return mav;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return new ModelAndView("error");
        }
    }

    @PostMapping("/car/rent/{id}")
    public String rentCar(@PathVariable Long id, @RequestParam Long customerId) {
        rentalCarService.rentCar(id, customerId);
        return "redirect:/car/" + id;
    }

    @PostMapping("/car/return/{id}")
    public String returnCar(@PathVariable Long id) {
        rentalCarService.returnCar(id);
        return "redirect:/car/" + id;
    }

    @GetMapping("/car/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        rentalCarService.deleteRentalCar(id);
        return "redirect:/cars";
    }

    @GetMapping("/car/edit/{id}")
    public ModelAndView showEditCarPage(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("edit-rental-car");
        RentalCar rentalCar = rentalCarService.getRentalCarById(id);
        mav.addObject("rentalCar", rentalCar);
        return mav;
    }

    @PostMapping("/car/update/{id}")
    public String updateCar(@PathVariable Long id, @ModelAttribute("rentalCar") RentalCar rentalCar, Model model) {
        try {
            rentalCarService.saveRentalCar(rentalCar);
            return "redirect:/cars";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }




}
