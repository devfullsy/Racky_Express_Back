package com.sen_system.services.impl;

import com.sen_system.dtos.UserMapperDTO;
import com.sen_system.entities.Clients;
import com.sen_system.repositories.UserRepository;
import com.sen_system.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public List<UserMapperDTO> listUsersByAdmin(){
        return userRepository.findAll().stream()
                .map(this::mapUserMapperDTO)
                .collect(Collectors.toList());
    }

    public ResponseEntity<UserMapperDTO> getUserInfoByUsernameByAdmin(String username){
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(mapUserMapperDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserMapperDTO> getUserInfoByEmailByAdmin(String email){
        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(mapUserMapperDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> updateUserByAdmin(String username, Clients updatedUser){
        return userRepository.findByUsername(username)
                .map(existingUser -> {
                    UserServiceImpl.check(updatedUser, existingUser);
                    userRepository.save(existingUser);
                    return ResponseEntity.ok("Mise à jour de l'utilisateur effectuée avec succès.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public boolean updateStateUserByAdmin(String username, Clients updatedUser) {
        return userRepository.findByUsername(username)
                .map(existingUser -> {
                    existingUser.setState(updatedUser.isState());
                    userRepository.save(existingUser);
                    return true; // Mise à jour réussie faire attention dans le front et gere la couleur du bouton si on envoi true ou false
                })
                .orElse(false); // Aucun utilisateur trouvé, mise à jour échouée
    }


    private UserMapperDTO mapUserMapperDTO(Clients user) {
        return new UserMapperDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getCompagny(),
                user.getCountry(),
                user.isEnabled()
        );
    }
}
