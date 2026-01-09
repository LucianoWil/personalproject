package com.ecomerceproject.personalproject.DTOs;

import lombok.Builder;

@Builder
public record ProductDTO(Long id, String name, double price, String description, Long categoryId, int stock, String imageUrl, boolean isFeatured) {}
