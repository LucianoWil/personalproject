package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.Model.CartItem;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import com.ecomerceproject.personalproject.Security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view/cart")
public class CartViewController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof JwtUserDetails) {
            username = ((JwtUserDetails) principal).username();
        } else {
            username = authentication.getName();
        }

        System.out.println(username);
        
        // Intentamos buscar por email primero, ya que es lo que usamos en el login
        Optional<User> userOptional = userRepository.findByEmail(username);
        
        // Si no se encuentra por email, intentamos por username (por si acaso)
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(username);
        }
        
        if (userOptional.isPresent()) {
            System.out.println("Hay usuario");
            User user = userOptional.get();
            List<CartItem> cart = user.getCart();
            System.out.println("Carrito: " + cart);
            model.addAttribute("cart", cart);
            model.addAttribute("user_id", user.getId());
        } else {
            System.out.println("NO hay usuario");
            // Manejar caso de usuario no encontrado o no logueado adecuadamente
            List<CartItem> cart = new ArrayList<>();
            model.addAttribute("cart", cart);
        }
        
        return "cart";
    }
}
