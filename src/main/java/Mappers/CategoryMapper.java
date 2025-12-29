package Mappers;

import com.ecomerceproject.personalproject.DTOs.CategoryDTO;
import com.ecomerceproject.personalproject.Model.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryDTO categoryDto) {
        return Category.builder().id(categoryDto.id()).name(categoryDto.name()).imageUrl(categoryDto.imageUrl()).build();
    }

    public static CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder().id(entity.getId()).name(entity.getName()).imageUrl(entity.getImageUrl()).build();
    }
}
