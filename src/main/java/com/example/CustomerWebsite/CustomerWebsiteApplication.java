package com.example.CustomerWebsite;


import com.example.CustomerWebsite.models.Customer;
import com.example.CustomerWebsite.models.CustomerService;
import com.example.CustomerWebsite.models.Role;
import com.example.CustomerWebsite.models.User;
import com.example.CustomerWebsite.repositories.RoleRepository;
import com.example.CustomerWebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class CustomerWebsiteApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CustomerWebsiteApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeData(CustomerService customerService) {
		return args -> {

			if (roleRepository.count() == 0) {

				Role userRole = new Role();
				userRole.setName("USER");
				roleRepository.save(userRole);

				Role adminRole = new Role();
				adminRole.setName("ADMIN");
				roleRepository.save(adminRole);
			}


			if (userRepository.count() == 0) {

				Role adminRole = roleRepository.findByName("ADMIN");

				User adminUser = new User();
				adminUser.setUsername("admin");
				adminUser.setPassword("admin");
				adminUser.setFullName("Admin User");
				adminUser.setRoles(Collections.singleton(adminRole));

				userRepository.save(adminUser);
			}


		};
	}

}


