package com.sen_system.controllers;

import com.sen_system.dtos.DriversMapperDTO;
import com.sen_system.dtos.RelayPointMapperDTO;
import com.sen_system.dtos.UserMapperDTO;
import com.sen_system.services.impl.DriverServiceImpl;
import com.sen_system.services.impl.RelayPointServiceImpl;
import com.sen_system.services.impl.UserAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relais/")
@RequiredArgsConstructor
public class RelayPointController {
    private final UserAdminServiceImpl userAdminService;
    private final RelayPointServiceImpl relayPointService;
    private final DriverServiceImpl driverService;

    @GetMapping("/username/{username}")
    public ResponseEntity<UserMapperDTO> getClientByUsername(@PathVariable String username) {
        return userAdminService.getUserInfoByUsernameByAdmin(username);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserMapperDTO> getClientByEmail(@PathVariable String email) {
        return userAdminService.getUserInfoByEmailByAdmin(email);
    }

    @GetMapping("/relayPoint-city/{city}")
    public List<RelayPointMapperDTO> getAllRelayPointsByCity(@PathVariable String city){
        return relayPointService.getRelayPointsByCity(city);
    }

    @GetMapping("/driver/{reference}")
    public ResponseEntity<DriversMapperDTO>  getDriverByReference(@PathVariable String reference){
        return driverService.getDriverByReference(reference);
    }
}
