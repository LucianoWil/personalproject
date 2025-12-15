package Mappers;

import com.ecomerceproject.personalproject.DTOs.ProductDTO;
import com.ecomerceproject.personalproject.Model.Product;

public class ProductMapper {

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder().id(productDTO.id()).name(productDTO.name()).price(productDTO.price()).description(productDTO.description()).categoryId(productDTO.categoryId()).stock(productDTO.stock()).build();
    }

    public static ProductDTO toDTO(Product entity) {
        return ProductDTO.builder().id(entity.getId()).name(entity.getName()).price(entity.getPrice()).description(entity.getDescription()).categoryId(entity.getCategoryId()).stock(entity.getStock()).build();
    }
}
