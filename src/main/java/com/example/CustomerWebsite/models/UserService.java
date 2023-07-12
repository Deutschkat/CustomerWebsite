package com.example.CustomerWebsite.models;

public interface UserService {

    boolean isUsernameTaken(String username);

    boolean isEmailRegistered(String email);

    void registerUser(User user);

    User getUser(Long id);

}