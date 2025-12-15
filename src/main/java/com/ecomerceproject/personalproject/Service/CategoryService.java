package com.ecomerceproject.personalproject.Service;

import Mappers.CategoryMapper;
import com.ecomerceproject.personalproject.DTOs.CategoryDTO;
import com.ecomerceproject.personalproject.Model.Category;
import com.ecomerceproject.personalproject.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
    }

    public Category getCategoryById(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            return categoryRepository.findById(id).get();
        }
        return null;
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        Category product = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDTO(categoryRepository.save(product));
    }

    public CategoryDTO update(Long id, CategoryDTO updatedDto) {
        Optional<Category> optional = categoryRepository.findById(id);

        if(optional.isPresent()) {
            Category category = optional.get();
            category.setName(updatedDto.name());
            category.setDescription(updatedDto.description());
            category.setProducts(updatedDto.products());

            return CategoryMapper.toDTO(categoryRepository.save(category));
        }
        else{
            throw new RuntimeException("Category with id" + id + " not found");
        }
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Category with id" + id + " not found");
        }
    }
}
