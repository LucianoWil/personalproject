package com.ecomerceproject.personalproject.DTOs;

import com.ecomerceproject.personalproject.Model.Product;
import com.ecomerceproject.personalproject.Model.User;
import lombok.Builder;

import java.util.List;

@Builder
public record CarritoDTO(Long id, List<Product> products, User user) {
}
