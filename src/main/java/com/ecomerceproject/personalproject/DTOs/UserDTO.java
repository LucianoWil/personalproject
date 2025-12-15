package com.ecomerceproject.personalproject.DTOs;

import lombok.Builder;

@Builder
public record UserDTO(Long id,String name, String email, String password, boolean admin) {}
