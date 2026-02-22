package com.example.doctorapp.controller;

import com.example.doctorapp.model.Role;
import com.example.doctorapp.model.User;
import com.example.doctorapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@RequestParam String fullName,
                               @RequestParam String email,
                               @RequestParam String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/signup?error=email";
        }
        User user = new User(email, passwordEncoder.encode(password), fullName, Role.PATIENT);
        userRepository.save(user);
        return "redirect:/login?registered";
    }
}
