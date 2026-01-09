package com.ecomerceproject.personalproject.Service;
import Mappers.ProductMapper;
import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Model.Category;
import com.ecomerceproject.personalproject.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecomerceproject.personalproject.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    // Método sobrecargado para cuando no se pasa categoría (devuelve todos)
    public List<ProductDTO> getAllProducts() {
        return getAllProducts(null);
    }

    // Método principal con la lógica
    public List<ProductDTO> getAllProducts(String category) {
        if (category != null && !category.isEmpty()){
            return productRepository.findByCategoryName(category).stream().map(ProductMapper::toDTO).collect(Collectors.toList());
        }
        return productRepository.findAll().stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> getByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ProductMapper.toDTO(product.get());
        }
        return null; // O podrías lanzar una excepción si prefieres
    }

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        Category category = categoryService.getCategoryById(dto.categoryId());
        product.setCategory(category);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    public ProductDTO update(Long id, ProductDTO updatedDto) {
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            Product product = optional.get();
            product.setName(updatedDto.name());
            product.setPrice(updatedDto.price());
            product.setDescription(updatedDto.description());

            Category category = categoryService.getCategoryById(updatedDto.categoryId());

            product.setCategory(category);
            product.setStock(updatedDto.stock());
            product.setImageUrl(updatedDto.imageUrl());

            return ProductMapper.toDTO(productRepository.save(product));
        }
        else{
            throw new RuntimeException("Product with id" + id + " not found");
        }
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Product with id" + id + " not found");
        }
    }

    public List<ProductDTO> getFeaturedProducts() {
        Optional<List <Product>> optional = Optional.ofNullable(productRepository.findByIsFeaturedTrue());
        return optional.map(products -> products.stream().map(ProductMapper::toDTO).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    public void setFeaturated(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setFeatured(!product.isFeatured());
            productRepository.save(product);
        }
    }
}
