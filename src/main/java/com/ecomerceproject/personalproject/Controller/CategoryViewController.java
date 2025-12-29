package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.CategoryDTO;
import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Model.Category;
import com.ecomerceproject.personalproject.Model.Product;
import com.ecomerceproject.personalproject.Service.CategoryService;
import Mappers.CategoryMapper;
import com.ecomerceproject.personalproject.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/view/categories")
public class CategoryViewController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryViewController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String getCategoryPage(@PathVariable Long id, Model model) {
        // Obtenemos la entidad del servicio
        Category category = categoryService.getCategoryById(id);

        // Convertimos a DTO para la vista (buena práctica)
        CategoryDTO categoryDTO = CategoryMapper.toDTO(category);

        List<ProductDTO> products = productService.getAllProducts(categoryDTO.name());

        // Agregamos el objeto al modelo para que Thymeleaf lo pueda usar
        model.addAttribute("category", categoryDTO);
        model.addAttribute("products", products);

        // Retornamos el nombre del archivo HTML (sin .html) que debe estar en resources/templates
        return "category";
    }

    @GetMapping("/list")
    public String getCategoryList(Model model) {
        List<CategoryDTO> categories;

        categories = categoryService.getAllCategories();
        System.out.println("No hay parametro");
        System.out.println(categories);

        model.addAttribute("categories", categories);
        return "categories";
    }
}
