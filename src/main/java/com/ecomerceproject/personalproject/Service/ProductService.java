package com.ecomerceproject.personalproject.Service;
import Mappers.ProductMapper;
import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecomerceproject.personalproject.Model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        }
        return null;
    }

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        return ProductMapper.toDTO(productRepository.save(product));
    }

    public ProductDTO update(Long id, ProductDTO updatedDto) {
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            Product product = optional.get();
            product.setName(updatedDto.name());
            product.setPrice(updatedDto.price());
            product.setDescription(updatedDto.description());
            product.setCategoryId(updatedDto.categoryId());
            product.setStock(updatedDto.stock());

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
}
