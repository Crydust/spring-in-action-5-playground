package com.example.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String registerForm(Model model) {
        model.addAttribute("registration", new RegistrationForm());
        return "registration";
    }

    @PostMapping("")
    public String processRegistration(
            @Valid @ModelAttribute(name = "registration") RegistrationForm form,
            Errors errors) {
        if (errors.hasErrors()) {
            return "registration";
        }

        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
