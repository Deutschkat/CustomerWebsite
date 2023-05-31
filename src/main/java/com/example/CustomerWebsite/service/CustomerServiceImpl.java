package com.example.CustomerWebsite.service;

import com.example.CustomerWebsite.models.Customer;
import com.example.CustomerWebsite.models.CustomerRepository;
import com.example.CustomerWebsite.models.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true )

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be empty.");
        }
        return customerRepository.save(customer);
    }
    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No customer with the ID number: " + id));
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id){
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("No customer with the ID number" + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Customer> saveAllCustomer(List<Customer> customerList){
        if (customerList == null || customerList.isEmpty()) {
            throw new IllegalArgumentException("Customer list cannot be empty.");
        }
        return customerRepository.saveAll(customerList);
    }

}
