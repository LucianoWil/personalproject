package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/view/products")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable Long id, Model model) {
        // Obtenemos el DTO directamente del servicio
        ProductDTO productDTO = productService.getProductById(id);

        // Agregamos el objeto al modelo para que Thymeleaf lo pueda usar
        model.addAttribute("product", productDTO);

        // Retornamos el nombre del archivo HTML (sin .html) que debe estar en resources/templates
        return "product"; 
    }

    @GetMapping("/list")
    public String getProductList(Model model, @RequestParam(required = false) String name) {
        List<ProductDTO> products;

        if (name != null && !name.isEmpty()) {
            // Si hay búsqueda, filtramos
            products = productService.getByName(name);
            System.out.println("HAY4 parametro");
            System.out.println(products);
        } else {
            products = productService.getAllProducts();
            System.out.println("No hay parametro");
            System.out.println(products);
        }

        model.addAttribute("products", products);
        model.addAttribute("name", name);
        return "products";
    }
}
