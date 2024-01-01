package com.sen_system.controllers;

import com.sen_system.dtos.DriversMapperDTO;
import com.sen_system.dtos.RelayPointMapperDTO;
import com.sen_system.entities.Clients;
import com.sen_system.entities.Drivers;
import com.sen_system.entities.RelayPoint;
import com.sen_system.services.impl.DriverServiceImpl;
import com.sen_system.services.impl.RelayPointServiceImpl;
import com.sen_system.services.impl.SupAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/supAdmin")
@RequiredArgsConstructor
public class SupAdminController {
    private final SupAdminServiceImpl supAdminService;
    private final RelayPointServiceImpl relayPointService;
    private final DriverServiceImpl driverService;

    // clients

    @GetMapping("/allClients")
    public List<Clients> getAllClients() {
        return supAdminService.getAllClientsBySupAdmin();
    }

    @GetMapping("/username/{username}")
    public Optional<Clients> getClientByUsername(@PathVariable String username) {
        return supAdminService.getClientByUsernameBySupAdmin(username);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<Clients> updateUser(@PathVariable String username, @Validated @RequestBody Clients updatedUser) {
        return supAdminService.updateUserBySupAdmin(username,updatedUser);
    }

    @PutMapping("/active/{username}")
    public ResponseEntity<Clients> disableUser(@PathVariable String username, @Validated @RequestBody Clients updatedUser) {
        return supAdminService.disableUserBySupAdmin(username,updatedUser);
    }

    @PutMapping("/change/role/{username}")
    public ResponseEntity<String> changeUserRole(@PathVariable String username){
        return supAdminService.changeUserRole(username);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        return supAdminService.deleteUserBySupAdmin(username);
    }

    // points de relais

    @GetMapping("/AllRelayPoints")
    public List<RelayPoint> getAllRelayPoints(){
        return relayPointService.getAllRelayPoints();
    }

    @GetMapping("/relayPoint/{relayPointName}")
    public ResponseEntity<RelayPointMapperDTO> getRelayPointBayName(@PathVariable String relayPointName){
        return relayPointService.getRelayPointByName(relayPointName);
    }

    @GetMapping("/relayPoint/{country}")
    public List<RelayPointMapperDTO> getAllRelayPointsByCountry(@PathVariable String country){
        return relayPointService.getRelayPointsByCountry(country);
    }

    @GetMapping("/relayPoint/{city}")
    public List<RelayPointMapperDTO> getAllRelayPointsByCity(@PathVariable String city){
        return relayPointService.getRelayPointsByCountry(city);
    }

    @GetMapping("/relayPoint/{district}")
    public List<RelayPointMapperDTO> getAllRelayPointsByDistrict(@PathVariable String district){
        return relayPointService.getRelayPointsByDistrict(district);
    }

    @DeleteMapping("/DeleteRelayPoint/{id}")
    public String deleteRelayPoint(@PathVariable Long id){
        return relayPointService.deleteRelayPoint(id);
    }


    // Tronsporteur

    @GetMapping("/AllDrivers")
    public List<Drivers> getAllDrivers(){
        return driverService.getAllDrivers();
    }

    @GetMapping("/driver/{reference}")
    public ResponseEntity<DriversMapperDTO>  getDriverByReference(@PathVariable String reference){
        return driverService.getDriverByReference(reference);
    }
    @DeleteMapping("/deleteDriver{reference}")
    public ResponseEntity<String> deleteDriver(@PathVariable String reference){
        return driverService.deleteDriver(reference);
    }
}
