package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.Model.Carrito;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/view/cart")
public class CartViewController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        // Intentamos buscar por email primero, ya que es lo que usamos en el login
        Optional<User> userOptional = userRepository.findByEmail(username);
        
        // Si no se encuentra por email, intentamos por username (por si acaso)
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(username);
        }
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Carrito carrito = user.getCarrito();
            model.addAttribute("carrito", carrito);
        } else {
            // Manejar caso de usuario no encontrado o no logueado adecuadamente
            model.addAttribute("carrito", new Carrito());
        }
        
        return "cart";
    }
}
