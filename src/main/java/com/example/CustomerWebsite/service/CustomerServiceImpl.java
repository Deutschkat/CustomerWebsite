package com.example.CustomerWebsite.service;

import com.example.CustomerWebsite.models.Customer;
import com.example.CustomerWebsite.models.CustomerRepository;
import com.example.CustomerWebsite.models.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

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
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Customer> saveAllCustomer(List<Customer> customerList){
        return customerRepository.saveAll(customerList);
    }

}
