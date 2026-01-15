package com.ecomerceproject.personalproject.Security;

public record JwtUserDetails (
        String username,
        String role
) {}