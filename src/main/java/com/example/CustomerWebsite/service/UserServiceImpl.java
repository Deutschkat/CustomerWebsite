package com.example.CustomerWebsite.service;

import com.example.CustomerWebsite.models.User;
import com.example.CustomerWebsite.models.UserService;
import com.example.CustomerWebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isUsernameTaken(String username) {

        User existingUser = userRepository.findByUsername(username);
        return existingUser != null;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        User existingUser = userRepository.findByEmail(email);
        return existingUser != null;
    }

    //WORKING HERE
    @Override
    public void registerUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }


}