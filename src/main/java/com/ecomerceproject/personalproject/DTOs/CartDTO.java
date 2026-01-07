package com.ecomerceproject.personalproject.DTOs;

import com.ecomerceproject.personalproject.Model.User;
import lombok.Builder;

import java.util.List;

@Builder
public record CartDTO(Long id, List<CartItemDTO> items, User user) {
}
