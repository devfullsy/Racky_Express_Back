package com.sen_system.services;

import com.sen_system.dtos.DocumentUploadDTO;
import com.sen_system.dtos.RelayPointDTO;
import com.sen_system.dtos.RelayPointMapperDTO;
import com.sen_system.entities.RelayPoint;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface RelayPointService {

    String createRelayPoint(RelayPointDTO relayPointDTO);
    List<RelayPoint> getAllRelayPoints();
    List<RelayPointMapperDTO> getRelayPointsByCountry( String country);
    List<RelayPointMapperDTO> getRelayPointsByCity( String city);
    List<RelayPointMapperDTO> getRelayPointsByDistrict(String district);
    List<RelayPointMapperDTO> getRelayPointByName(String relayPointName);
    ResponseEntity<String> updateRelayPointById(Long id, RelayPoint updatedRelayPoint);

    RelayPointMapperDTO getRelayPointByUsername(String username);
    String deleteRelayPoint( Long id);

    String saveRelailleurIdCard(String username, DocumentUploadDTO uploadDTO) throws IOException;

}
