package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.CategoryDTO;
import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Service.CategoryService;
import com.ecomerceproject.personalproject.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view/products/load")
public class loadProductViewController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public loadProductViewController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String loadProduct(Model model){
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "load-product";
    }
    @PostMapping
    public String loadProductProcess(@ModelAttribute ProductDTO productDTO, Model model){

        try {
            productService.createProduct(productDTO);
            return "redirect:/view/products/list";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "load-product";
        }
    }
}
