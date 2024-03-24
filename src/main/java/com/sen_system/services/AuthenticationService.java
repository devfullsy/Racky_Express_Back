package com.sen_system.services;

import com.sen_system.dtos.*;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticationService {

    String signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void changePassword(ChangePwdRequest changePwdRequest);
}
