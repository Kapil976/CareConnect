package com.kg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kg.entity.User;
import com.kg.service.UserService;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; // Ensure this matches your login.html file name
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf template name
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "register"; // Return to the registration form with validation errors
        }

        // Check if the username already exists
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("backendError", "Username already exists, please use different username");
            return "register"; // Return to registration page with error
        }

        // Optionally, check if the passwords match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("backendError", "Passwords do not match");
            return "register"; // Return to registration page with error
        }

        // Register the user
        userService.registerUser(user);

        // Redirect to login after successful registration
        return "redirect:/login"; // Ensure /login is a valid endpoint
    }

}
