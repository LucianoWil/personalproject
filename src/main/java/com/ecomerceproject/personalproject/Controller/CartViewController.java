package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.Model.CartItem;
import com.ecomerceproject.personalproject.Model.Product;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import com.ecomerceproject.personalproject.Security.JwtUserDetails;
import com.ecomerceproject.personalproject.Service.ProductService;
import com.ecomerceproject.personalproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view/cart")
public class CartViewController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

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

        Optional<User> userOptional = userRepository.findByEmail(username);
        
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(username);
        }
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<CartItem> cart = user.getCart();
            model.addAttribute("cart", cart);
            model.addAttribute("user_id", user.getId());
        } else {
            List<CartItem> cart = new ArrayList<>();
            model.addAttribute("cart", cart);
        }
        
        return "cart";
    }

    @GetMapping("/emptyCart/{isPurchase}")
    public String emptyCart(Model model, RedirectAttributes redirectAttributes, @PathVariable boolean isPurchase){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof JwtUserDetails) {
            username = ((JwtUserDetails) principal).username();
        } else {
            username = authentication.getName();
        }

        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(username);
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<CartItem> cart = user.getCart();
            if(isPurchase){
                for (int i = 0; i < cart.size(); i++){
                    CartItem auxCartItem = cart.get(i);
                    Product auxProduct = auxCartItem.getProduct();
                    productService.setProductStock(auxProduct.getId(), auxProduct.getStock() - auxCartItem.getQuantity());
                }
            }
            userService.emptyCart(user.getId());

            if (isPurchase){
                return "redirect:/view/index";
            }
            return "redirect:/view/cart";
        }

        if (isPurchase){
            redirectAttributes.addFlashAttribute("mensaje", "No se pudo vaciar el carrito");
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        else{
            redirectAttributes.addFlashAttribute("mensaje", "No se pudo realizar la compra");
            redirectAttributes.addFlashAttribute("tipo", "error");
        }
        return "redirect:/view/cart";
    }
}
