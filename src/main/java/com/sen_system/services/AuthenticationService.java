package com.sen_system.services;

import com.sen_system.dtos.JwtAuthenticationResponse;
import com.sen_system.dtos.RefreshTokenRequest;
import com.sen_system.dtos.SignUpRequest;
import com.sen_system.dtos.SigninRequest;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    String signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
