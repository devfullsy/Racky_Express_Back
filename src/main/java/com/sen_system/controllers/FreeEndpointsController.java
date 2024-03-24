package com.sen_system.controllers;

import com.sen_system.dtos.RelayPointMapperDTO;
import com.sen_system.services.impl.RelayPointServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FreeEndpointsController {

    private final RelayPointServiceImpl relayPointService;


    //les endpoints pour les points relais

    @GetMapping("/relayName/{relayPointName}")
    public List<RelayPointMapperDTO> getRelayPointBayName(@PathVariable String relayPointName){
        return relayPointService.getRelayPointByName(relayPointName);
    }

    @GetMapping("/country/{country}")
    public List<RelayPointMapperDTO> getAllRelayPointsByCountry(@PathVariable String country){
        return relayPointService.getRelayPointsByCountry(country);
    }

    @GetMapping("/city/{city}")
    public List<RelayPointMapperDTO> getAllRelayPointsByCity(@PathVariable String city){
        return relayPointService.getRelayPointsByCity(city);
    }

    @GetMapping("/district/{district}")
    public List<RelayPointMapperDTO> getAllRelayPointsByDistrict(@PathVariable String district){
        return relayPointService.getRelayPointsByDistrict(district);
    }

}
