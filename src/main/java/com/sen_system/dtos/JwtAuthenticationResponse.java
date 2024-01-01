package com.sen_system.dtos;

import com.sen_system.entities.Role;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;
    private String refreshToken;
    private Role role;
}
