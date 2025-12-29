package com.ecomerceproject.personalproject.DTOs;

import lombok.Builder;

@Builder
public record UserDTO(Long id,String username, String email, String password, String role) {}
