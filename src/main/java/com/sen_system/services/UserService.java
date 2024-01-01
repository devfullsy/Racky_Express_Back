package com.sen_system.services;

import com.sen_system.dtos.UserDTO;
import com.sen_system.entities.Clients;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {

    UserDetailsService userDetailsService();
    ResponseEntity<UserDTO> userInfo(String username);
    void updateUser(String username, Clients updatedUser);

    String saveUserProfileImage(String username, MultipartFile file) throws IOException;
    byte[] getUserProfileImage(String username) throws IOException;

}
