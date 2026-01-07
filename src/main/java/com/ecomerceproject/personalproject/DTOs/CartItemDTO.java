package com.ecomerceproject.personalproject.DTOs;

import com.ecomerceproject.personalproject.Model.Product;
import lombok.Builder;

@Builder
public record CartItemDTO(Long id, Product product, int quantity) {
}
