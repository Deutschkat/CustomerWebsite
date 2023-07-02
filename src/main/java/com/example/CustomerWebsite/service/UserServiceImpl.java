package com.example.CustomerWebsite.service;

import com.example.CustomerWebsite.models.User;
import com.example.CustomerWebsite.models.UserService;
import com.example.CustomerWebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isUsernameTaken(String username) {
        // Implementation to check if the username is already taken in the database
        // You can use userRepository.findByUsername(username) to query the database
        // and check if the result is not null
        User existingUser = userRepository.findByUsername(username);
        return existingUser != null;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        // Implementation to check if the email is already registered in the database
        // You can use userRepository.findByEmail(email) to query the database
        // and check if the result is not null
        User existingUser = userRepository.findByEmail(email);
        return existingUser != null;
    }

    @Override
    public void registerUser(User user) {
        // Implementation to save the user to the database
        // You can use userRepository.save(user) to persist the user entity
        userRepository.save(user);
    }

    // Other methods here
}