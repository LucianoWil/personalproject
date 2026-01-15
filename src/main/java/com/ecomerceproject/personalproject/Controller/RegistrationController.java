package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.UserDTO;
import com.ecomerceproject.personalproject.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        // Pasamos un objeto vacío para que Thymeleaf pueda vincular los campos
        // Aunque en tu HTML actual usas name="campo" directo, es buena práctica
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute UserDTO userDTO, Model model) {
        try {
            userService.createUser(userDTO);
            return "verification-sent";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al registrar el usuario.");
            return "register";
        }
    }
}
