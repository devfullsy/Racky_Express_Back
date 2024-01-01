package com.sen_system.controllers;

import com.sen_system.dtos.*;
import com.sen_system.entities.Clients;
import com.sen_system.entities.RelayPoint;
import com.sen_system.services.impl.DriverServiceImpl;
import com.sen_system.services.impl.RelayPointServiceImpl;
import com.sen_system.services.impl.UserAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserAdminServiceImpl userAdminService;
    private final RelayPointServiceImpl relayPointService;
    private final DriverServiceImpl driverService;

    // les endpoints pour le client

    @GetMapping("/allClients")
    public List<UserMapperDTO> getAllClients() {
        return userAdminService.listUsersByAdmin();
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<UserMapperDTO> getClientByUsername(@PathVariable String username) {
        return userAdminService.getUserInfoByUsernameByAdmin(username);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserMapperDTO> getClientByEmail(@PathVariable String email) {
        return userAdminService.getUserInfoByEmailByAdmin(email);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @Validated @RequestBody Clients updatedUser) {
        return userAdminService.updateUserByAdmin(username,updatedUser);
    }

    @PutMapping("/active/{username}")
    public boolean disableUser(@PathVariable String username, @Validated @RequestBody Clients updatedUser) {
        return userAdminService.updateStateUserByAdmin(username,updatedUser);
    }


    //les endpoints pour les points relais

    @PostMapping("/createRelayPoint")
    public ResponseEntity<String> createRelayPointByAdmin(@RequestBody RelayPointDTO relayPointDTO){
        return ResponseEntity.ok(relayPointService.createRelayPoint(relayPointDTO));
    }

    @PutMapping("/relayPoint-idCard/{username}")
    public ResponseEntity<String> uploadProfileImage(@PathVariable String username, @ModelAttribute DocumentUploadDTO uploadDTO) {
        try {
            if (relayPointService.getRelayPointByName(username) == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(relayPointService.saveRelailleurIdCard(username, uploadDTO));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du document.");
        }
    }

    @GetMapping("/relayPoint-name/{relayPointName}")
    public ResponseEntity<RelayPointMapperDTO> getRelayPointBayName(@PathVariable String relayPointName){
        return relayPointService.getRelayPointByName(relayPointName);
    }

    @GetMapping("/relayPoint-country/{country}")
    public List<RelayPointMapperDTO> getAllRelayPointsByCountry(@PathVariable String country){
        return relayPointService.getRelayPointsByCountry(country);
    }

    @GetMapping("/relayPoint-city/{city}")
    public List<RelayPointMapperDTO> getAllRelayPointsByCity(@PathVariable String city){
        return relayPointService.getRelayPointsByCity(city);
    }

    @GetMapping("/relayPoint-district/{district}")
    public List<RelayPointMapperDTO> getAllRelayPointsByDistrict(@PathVariable String district){
        return relayPointService.getRelayPointsByDistrict(district);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRelayPoint(@PathVariable Long id, @Validated @RequestBody RelayPoint relayPoint){
        return relayPointService.updateRelayPointById(id,relayPoint);
    }


    //endpoints pour les transporteurs

    @PostMapping("/createDriver")
    public ResponseEntity<String> createDriverByAdmin(DriversDTO driversDTO){
        return ResponseEntity.ok(driverService.createDriver(driversDTO));
    }

    @GetMapping("/driver/{reference}")
    public ResponseEntity<DriversMapperDTO>  getDriverByReference(@PathVariable String reference){
        return driverService.getDriverByReference(reference);
    }

    @PutMapping("/updateDrive/{reference}")
    public ResponseEntity<String> updateDriverByAdmin(@PathVariable String reference, @Validated @RequestBody DriversDTO driversDTO){
        return driverService.updateDriver(reference,driversDTO);
    }

}
