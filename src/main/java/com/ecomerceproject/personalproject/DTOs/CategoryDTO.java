package com.ecomerceproject.personalproject.DTOs;

import com.ecomerceproject.personalproject.Model.Product;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryDTO(Long id,String name, String description, List<Product> products){}