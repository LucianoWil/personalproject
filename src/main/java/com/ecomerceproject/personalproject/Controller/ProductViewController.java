package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import com.ecomerceproject.personalproject.Security.JwtUserDetails;
import com.ecomerceproject.personalproject.Service.CartItemService;
import com.ecomerceproject.personalproject.Service.CategoryService;
import com.ecomerceproject.personalproject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view/products")
public class ProductViewController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final CartItemService cartItemService;

    @Autowired
    private UserRepository userRepository;
    private final CategoryService categoryService;

    public ProductViewController(ProductService productService, CartItemService cartItemService, CategoryService categoryService) {
        this.productService = productService;
        this.cartItemService = cartItemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof JwtUserDetails) {
            username = ((JwtUserDetails) principal).username();
        } else {
            username = authentication.getName();
        }

        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user_id", user.getId());
        }
        else{
            //No logueado
            model.addAttribute("user_id", "-1");
        }

        // Obtenemos el DTO directamente del servicio
        ProductDTO productDTO = productService.getProductById(id);
        String category = categoryService.getCategoryById(productDTO.categoryId()).getName();

        // Agregamos el objeto al modelo para que Thymeleaf lo pueda usar
        model.addAttribute("product", productDTO);
        model.addAttribute("category", category);

        // Retornamos el nombre del archivo HTML (sin .html) que debe estar en resources/templates
        return "product"; 
    }

    @GetMapping("/list")
    public String getProductList(Model model, @RequestParam(required = false) String name) {
        List<ProductDTO> products;

        if (name != null && !name.isEmpty()) {
            // Si hay búsqueda, filtramos
            products = productService.getByName(name);
        }
        else {
            products = productService.getAllProducts();
            System.out.println(products);
        }

        model.addAttribute("products", products);
        model.addAttribute("name", name);
        return "products";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        try {
            cartItemService.deleteCartItemByProduct(id);
            productService.deleteProduct(id);
        } catch (Exception e) {
            System.err.println("Error al borrar el producto: " + e.getMessage());
        }

        return "redirect:/view/products/list";
    }
}
