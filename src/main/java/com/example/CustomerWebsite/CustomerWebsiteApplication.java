package com.example.CustomerWebsite;

import com.example.CustomerWebsite.models.Customer;
import com.example.CustomerWebsite.models.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class CustomerWebsiteApplication {

	@Autowired
	private CustomerService customerService;


	public static void main(String[] args) {
		SpringApplication.run(CustomerWebsiteApplication.class, args);
	}




	@Bean
	public CommandLineRunner loadInitialData(CustomerService customerService) {
		return (args) -> {
			if (customerService.getAllCustomers().isEmpty()) {
				customerService.saveAllCustomer(Arrays.asList(
						Customer.builder().fullName("Customer 1").emailAddress("customer1@gmail.com").address("Customer Address One").age(30).build(),
						Customer.builder().fullName("Customer 2").emailAddress("customer2@gmail.com").address("Customer Address Two").age(28).build(),
						Customer.builder().fullName("Customer 3").emailAddress("customer3@gmail.com").address("Customer Address Three").age(32).build()));
			}
		};
	}
}



