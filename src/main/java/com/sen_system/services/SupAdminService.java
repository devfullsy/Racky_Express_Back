package com.sen_system.services;

import com.sen_system.entities.Clients;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SupAdminService {
    List<Clients> getAllClientsBySupAdmin();
    Optional<Clients> getClientByUsernameBySupAdmin(String username);
    ResponseEntity<Clients> updateUserBySupAdmin(String username, Clients updatedUser);
    ResponseEntity<Clients> disableUserBySupAdmin(String username,Clients updatedUser);
    ResponseEntity<String> deleteUserBySupAdmin( String username);

    ResponseEntity<String> changeUserRole(String username);
}
