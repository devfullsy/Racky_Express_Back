package com.sen_system.controllers;


import com.sen_system.dtos.RelayPointMapperDTO;
import com.sen_system.dtos.UserDTO;
import com.sen_system.entities.Clients;
import com.sen_system.services.impl.RelayPointServiceImpl;
import com.sen_system.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final UserServiceImpl userService;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @GetMapping("/profile/info")
    public ResponseEntity<UserDTO> getInfoClient() {
       UserDTO userDTO = userService.userInfo(username).getBody();
        return ResponseEntity.ok(userDTO);
    }


    @PutMapping("/update")
    public String updateClient(@Validated @RequestBody Clients updatedUser) {
        userService.updateUser(username, updatedUser);
        return "votre compte est à jour";
    }

    @PutMapping("/profile/image/{username}")
    public ResponseEntity<String> uploadProfileImage(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(userService.saveUserProfileImage(username, file));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de la photo de profil.");
        }
    }
}
