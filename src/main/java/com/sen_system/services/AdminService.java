package com.sen_system.services;

import com.sen_system.dtos.UserMapperDTO;
import com.sen_system.entities.Clients;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {


    List<UserMapperDTO> listUsersByAdmin();
    ResponseEntity<UserMapperDTO> getUserInfoByUsernameByAdmin(String username);
    ResponseEntity<UserMapperDTO> getUserInfoByEmailByAdmin(String email);
    ResponseEntity<String> updateUserByAdmin(String username, Clients updatedUser);

    boolean updateStateUserByAdmin(String username, Clients updatedUser);
}
