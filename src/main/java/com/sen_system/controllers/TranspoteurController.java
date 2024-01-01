package com.sen_system.controllers;

import com.sen_system.dtos.DriversMapperDTO;
import com.sen_system.services.impl.DriverServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transporteur")
@RequiredArgsConstructor
public class TranspoteurController {
    private final DriverServiceImpl driverService;

    @GetMapping("/getDriver/{username}")
    public ResponseEntity<DriversMapperDTO> getDriverByUsername(@PathVariable String username){
        return driverService.getDriverByUsername(username);
    }

    @GetMapping("/getDriverByRef/{reference}")
    public ResponseEntity<DriversMapperDTO> getDriverByReference(@PathVariable String reference){
        return driverService.getDriverByReference(reference);
    }
}
