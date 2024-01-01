package com.sen_system.services;

import com.sen_system.dtos.DriversDTO;
import com.sen_system.dtos.DriversMapperDTO;
import com.sen_system.entities.Drivers;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DriversService {

    String createDriver(DriversDTO driversDTO);
    List<Drivers> getAllDrivers();
    ResponseEntity<DriversMapperDTO> getDriverByReference(String reference);

    ResponseEntity<DriversMapperDTO> getDriverByUsername(String username);
    ResponseEntity<String> updateDriver(String reference, DriversDTO driver);
    ResponseEntity<String> deleteDriver(String reference);

}
