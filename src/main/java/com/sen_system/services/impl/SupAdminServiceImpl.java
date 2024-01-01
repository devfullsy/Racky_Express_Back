package com.sen_system.services.impl;

import com.sen_system.entities.Clients;
import com.sen_system.entities.Role;
import com.sen_system.repositories.UserRepository;
import com.sen_system.services.SupAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupAdminServiceImpl implements SupAdminService {
    private final UserRepository userRepository;

    public List<Clients> getAllClientsBySupAdmin() {
        return userRepository.findAll();
    }
    public Optional<Clients> getClientByUsernameBySupAdmin(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<Clients> updateUserBySupAdmin(String username,Clients updatedUser) {
        return userRepository.findByUsername(username)
                .map(existingUser -> {
                    UserServiceImpl.check(updatedUser, existingUser);
                    return ResponseEntity.ok(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<Clients> disableUserBySupAdmin(String username,Clients updatedUser) {
        return userRepository.findByUsername(username)
                .map(existingUser -> {
                    existingUser.setState(updatedUser.isState());
                    return ResponseEntity.ok(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> deleteUserBySupAdmin( String username) {
        Optional<Clients> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.ok(username + " a été supprimé.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur : " + username+" non trouvé ");
        }
    }

    public ResponseEntity<String> changeUserRole(String username){
        Optional<Clients> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            Clients user = optionalUser.get();
            user.setRole(Role.ADMIN);
            return ResponseEntity.ok("le role ADMINISTRATEUR à bien été appliqué "+username);
        }
        return ResponseEntity.ok("Cette utilisateur n'existe pas");
    }

}
