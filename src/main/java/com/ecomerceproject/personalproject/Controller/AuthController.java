package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.UserDTO;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import com.ecomerceproject.personalproject.Security.JwtService;
import com.ecomerceproject.personalproject.Security.JwtUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response, Model model) {
        // Intentamos buscar por email
        Optional<User> userOptional = userRepository.findByEmail(username);
        
        // Si no se encuentra por email, intentamos por username
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(username);
        }
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Verificamos la contraseña encriptada
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Autenticación exitosa
                JwtUserDetails userDetails = new JwtUserDetails(user.getEmail(), "ROLE_" + user.getRole().toUpperCase());
                String token = jwtService.createToken(userDetails);
                
                // Crear cookie
                Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(24 * 60 * 60); // 1 día
                
                response.addCookie(jwtCookie);
                
                return "redirect:/view/products/list";
            }
        }
        
        // Fallo de autenticación
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }
}
