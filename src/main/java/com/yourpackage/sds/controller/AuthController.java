package com.yourpackage.sds.controller;

import com.yourpackage.sds.dto.RegisterRequest;
import com.yourpackage.sds.exception.EmailAlreadyExistsException;
import com.yourpackage.sds.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute("user") RegisterRequest form,
                                BindingResult bindingResult,
                                Model model,
                                org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        if (form.getPassword() != null && form.getConfirmPassword() != null 
                && !form.getPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(form);
            redirectAttributes.addFlashAttribute("successMessage", "Registration is successful! Please log in.");
        } catch (EmailAlreadyExistsException ex) {
            model.addAttribute("emailError", ex.getMessage());
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "error/403";
    }
}
