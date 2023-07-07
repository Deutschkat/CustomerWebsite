package com.example.CustomerWebsite.controller;

import com.example.CustomerWebsite.models.User;
import com.example.CustomerWebsite.models.UserService;
import com.example.CustomerWebsite.repositories.RoleRepository;
import com.example.CustomerWebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.CustomerWebsite.models.Role;
import javax.validation.Valid;
import java.util.Collections;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            boolean isUsernameTaken = userRepository.findByUsername(user.getUsername()) != null;
            boolean isEmailRegistered = userRepository.findByEmail(user.getEmail()) != null;

            if (isUsernameTaken) {
                model.addAttribute("errorMessage", "Username is already taken");
                return "register";
            }

            if (isEmailRegistered) {
                model.addAttribute("errorMessage", "Email is already registered");
                return "register";
            }

            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                throw new RuntimeException("User role not found");
            }
            user.setRoles(Collections.singleton(userRole));

            userRepository.save(user);
            return "redirect:/login";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register";
        }
    }


}