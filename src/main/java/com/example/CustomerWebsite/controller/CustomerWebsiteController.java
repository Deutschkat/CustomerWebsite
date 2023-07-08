package com.example.CustomerWebsite.controller;

import com.example.CustomerWebsite.models.Customer;
import com.example.CustomerWebsite.models.CustomerService;
import com.example.CustomerWebsite.models.RentalCar;
import com.example.CustomerWebsite.models.RentalCarService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerWebsiteController {

    @Autowired
    private final CustomerService customerService;
    private final RentalCarService rentalCarService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";

    }

    @GetMapping("/customer-list")
    public String customerList(Model model) {
        final List<Customer> customerList = customerService.getAllCustomers();
        model.addAttribute("customerList", customerList);
        return "customer-list";
    }

    @GetMapping("/new")
    public String showNewCustomerPage(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "new-customer";

    }

    @PostMapping(value = "/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer, Model model){
        try {
            customerService.saveCustomer(customer);
            return "redirect:/customer-list";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditCustomerPage(@PathVariable(name = "id") Long id, Model model){
        try {
            ModelAndView mav = new ModelAndView("edit-customer");
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

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable(name = "id") Long id, @ModelAttribute("customer") Customer customer, Model model) {
        try {
            if (customer.getCarId() != null) {
                rentalCarService.rentCar(customer.getCarId(), id);
            }

            customerService.saveCustomer(customer);
            return "redirect:/customer-list";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id, Model model) {
        try {
            Customer customer = customerService.getCustomer(id);
            if (customer == null) {
                throw new IllegalArgumentException("Sorry, cant delete . No customer with the id number: " + id + ".");
            }
            customerService.deleteCustomer(id);
            return "redirect:/customer-list";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
    }

}
