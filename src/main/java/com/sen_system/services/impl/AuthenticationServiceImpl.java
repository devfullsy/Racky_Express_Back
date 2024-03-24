package com.sen_system.services.impl;

import com.sen_system.controllers.ClientController;
import com.sen_system.dtos.*;
import com.sen_system.entities.Clients;
import com.sen_system.entities.Role;
import com.sen_system.repositories.UserRepository;
import com.sen_system.services.AuthenticationService;
import com.sen_system.services.JWTService;
import com.sen_system.utils.DownloadPicture;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;
    private final ClientController clt;
    @Value("${profil.upload.directory}")
    private String uploadDirectory;
    private final DownloadPicture downloadPicture;

    public String signup(SignUpRequest signUpRequest){
        if (userRepository.findByUsername(signUpRequest.getUsername()).isEmpty()
                || userRepository.findByUsername(signUpRequest.getEmail()).isEmpty()) {
        String photo = downloadPicture.initialProfilPicture(signUpRequest.getUsername(),uploadDirectory) ;
        Clients user = new Clients();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setCountry(signUpRequest.getCountry());
        user.setProfileImage(photo);
        user.setRole(Role.CLIENT);
        userRepository.save(user);

       return "Compte créer";
        }
        return "Nom d'utilisateur ou E-mail déjà utilisé";
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signinRequest.getUsername(),
                signinRequest.getPassword()));
        var user = userRepository.findByUsername(signinRequest.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("le nom d'utilisateur ou le mot de passe est incorrecte. "));
        clt.setUsername(signinRequest.getUsername());
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRole(user.getRole());

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userName = jwtService.extractUserName(refreshTokenRequest.getToken());
        Clients user = userRepository.findByUsername(userName).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){

            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }

        return null;
    }

    @Override
    public void changePassword(ChangePwdRequest changePwdRequest) {
        Optional<Clients> optionalUser = userRepository.findByUsername(changePwdRequest.getUsername());

        if (optionalUser.isPresent()){
            Clients user = optionalUser.get();
            if(!changePwdRequest.getNewPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(changePwdRequest.getNewPassword()));
                userRepository.save(user);
            }
        }
    }
}
