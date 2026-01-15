package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.CategoryDTO;
import com.ecomerceproject.personalproject.Model.Category;
import com.ecomerceproject.personalproject.Repository.CartItemRepository;
import com.ecomerceproject.personalproject.Service.CartService;
import com.ecomerceproject.personalproject.Service.CategoryService;
import com.ecomerceproject.personalproject.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    public CategoryController(CategoryService categoryService, ProductService productService, CartService cartService, CartItemRepository cartItemRepository) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartService = cartService;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO updatedDto) {
        return ResponseEntity.ok(categoryService.update(id, updatedDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            // 1. Eliminar items del carrito que contengan productos de esta categoría
            cartItemRepository.deleteByProductCategoryId(id);
            
            // 2. Eliminar los productos de la categoría
            productService.deleteAllProducts(id);
            
            // 3. Eliminar la categoría
            categoryService.deleteCategory(id);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
