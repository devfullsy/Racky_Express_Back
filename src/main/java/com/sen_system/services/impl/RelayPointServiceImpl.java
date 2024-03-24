package com.sen_system.services.impl;

import com.sen_system.dtos.DocumentUploadDTO;
import com.sen_system.dtos.RelayPointDTO;
import com.sen_system.dtos.RelayPointMapperDTO;
import com.sen_system.entities.Clients;
import com.sen_system.entities.RelayPoint;
import com.sen_system.entities.Role;
import com.sen_system.repositories.RelayPointRepository;
import com.sen_system.repositories.UserRepository;
import com.sen_system.services.RelayPointService;
import com.sen_system.utils.FileVerifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelayPointServiceImpl implements RelayPointService {

    private final FileVerifications fileVerifications;
    private final RelayPointRepository relayPointRepository;
    private final UserRepository userRepository;
    @Value("${idCard.upload.directory}")
    private String uploadDirectory;

    @Override
    public String createRelayPoint(RelayPointDTO relayPointDTO) {
        Optional<Clients> userOptional = userRepository.findByUsername(relayPointDTO.getUsername());
        if(userOptional.isPresent()){
            if (relayPointRepository.findRelayPointByName(relayPointDTO.getName()).isEmpty()) {
                Clients user = userOptional.get();
                RelayPoint relayPoint = new RelayPoint();
                relayPoint.setUsername(user.getUsername());
                relayPoint.setCountry(user.getCountry());
                relayPoint.setPhoneNumber(user.getPhoneNumber());
                relayPoint.setEmail(user.getEmail());
                if(relayPointDTO.getName()!= null){relayPoint.setName(relayPointDTO.getName()); }
                if( relayPointDTO.getCity()!=null){relayPoint.setCity(relayPointDTO.getCity()); }
                if( relayPointDTO.getAddress()!=null){relayPoint.setAddress(relayPointDTO.getAddress()); }
                if( relayPointDTO.getDescription()!=null){relayPoint.setDescription(relayPointDTO.getDescription());}
                if( relayPointDTO.getOpenAtCloseAt()!=null){relayPoint.setOpenAtCloseAt(relayPointDTO.getOpenAtCloseAt());}
                if( relayPointDTO.getDistrict()!=null){relayPoint.setDistrict(relayPointDTO.getDistrict());}
                if( relayPointDTO.getStatus()!=null){relayPoint.setStatus(relayPointDTO.getStatus());}
                user.setRole(Role.RELAILLEUR);
                userRepository.save(user);
                relayPointRepository.save(relayPoint);


                return "Le point relais a bien été crée";

            }
            return "Il existe déjà un point relais avec ce nom";
        }
        return "Nom d'utilisateur incorrecte ";
    }

    @Override
    public List<RelayPoint> getAllRelayPoints() {
        return relayPointRepository.findAll();
    }

    @Override
    public List<RelayPointMapperDTO> getRelayPointsByCountry(String country) {
        List<RelayPoint> relayPoints = relayPointRepository.findRelayPointsByCountry(country);
        return relayPoints.stream()
                .map(this::relayPointDTOMapper)
                .collect(Collectors.toList());

    }

    @Override
    public List<RelayPointMapperDTO> getRelayPointsByCity(String city) {
        List<RelayPoint> relayPoints = relayPointRepository.findRelayPointsByCity(city);
        return relayPoints.stream()
                .map(this::relayPointDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<RelayPointMapperDTO> getRelayPointsByDistrict(String district) {
        List<RelayPoint> relayPoints = relayPointRepository.findRelayPointsByDistrict(district);
        return relayPoints.stream()
                .map(this::relayPointDTOMapper)
                .collect(Collectors.toList());

    }

    @Override
    public List<RelayPointMapperDTO> getRelayPointByName(String relayPointName) {
        Optional<RelayPoint> relayPoints = relayPointRepository.findRelayPointByName(relayPointName);
        return relayPoints.stream()
                .map(this::relayPointDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> updateRelayPointById(Long id, RelayPoint updatedRelayPoint) {
        return relayPointRepository.findRelayPointById(id)
                .map(existingRelaypoint -> {
                    checkRelayPoint(updatedRelayPoint,existingRelaypoint);
                    relayPointRepository.save(existingRelaypoint);
                    return ResponseEntity.ok("Mise à jour du point relais effectuée avec succès.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public RelayPointMapperDTO getRelayPointByUsername(String username) {
        Optional<RelayPoint> optionalRelayPoint = relayPointRepository.findRelayPointByUsername(username);
            return optionalRelayPoint.map(this::relayPointDTOMapper).orElse(null);
    }

    @Override
    public String deleteRelayPoint(Long id) {
        Optional<RelayPoint> optionalRelayPoint = relayPointRepository.findRelayPointById(id);
        optionalRelayPoint.ifPresent(relayPointRepository::delete);
        return "le point relais a bien été supprimé ";
    }

    @Override
    public String saveRelailleurIdCard(String username, DocumentUploadDTO uploadDTO) throws IOException {
        if(fileVerifications.checkExpirationDate(String.valueOf(uploadDTO.getExpirationDate())).isAfter(LocalDate.now())){
            Optional<RelayPoint> optionalRelayPoint = relayPointRepository.findRelayPointByUsername(username);
            if (optionalRelayPoint.isPresent()) {
                RelayPoint relayPoint = optionalRelayPoint.get();
                relayPoint.setIdCard(fileVerifications.createDir(username,uploadDTO.getFile(),uploadDirectory));
                relayPoint.setDocumentType(uploadDTO.getDocumentType());
                relayPoint.setExpirationDate(uploadDTO.getExpirationDate());
                relayPoint.setRegistrationNumber(uploadDTO.getDocumentNumber());
                relayPointRepository.save(relayPoint);
                return "Les informations sont valides.";
            } else {
                throw new EntityNotFoundException("Utilisateur non trouvé ");
            }
        } else {
            return "La date d'expiration n'est pas valide.";
        }
    }

    public void checkRelayPoint(RelayPoint updatedRelayPoint, RelayPoint existingRelaypoint) {
        Optional<Clients> userOptional = userRepository.findByUsername(existingRelaypoint.getUsername());
        Clients user = userOptional.get();
        if (updatedRelayPoint.getName()!= null){existingRelaypoint.setName(updatedRelayPoint.getName());}
        if (updatedRelayPoint.getCountry()!= null){existingRelaypoint.setCountry(updatedRelayPoint.getCountry());user.setCountry(updatedRelayPoint.getCountry());}
        if (updatedRelayPoint.getCity()!= null){existingRelaypoint.setCity(updatedRelayPoint.getCity());}
        if (updatedRelayPoint.getDistrict()!= null){existingRelaypoint.setDistrict(updatedRelayPoint.getDistrict());}
        if (updatedRelayPoint.getPhoneNumber()!= null){existingRelaypoint.setPhoneNumber(updatedRelayPoint.getPhoneNumber());user.setCountry(updatedRelayPoint.getPhoneNumber());}
        if (updatedRelayPoint.getEmail()!= null){existingRelaypoint.setEmail(updatedRelayPoint.getEmail());user.setCountry(updatedRelayPoint.getEmail());}
        if (updatedRelayPoint.getAddress()!= null){existingRelaypoint.setAddress(updatedRelayPoint.getAddress());}
        if (updatedRelayPoint.getDescription()!= null){existingRelaypoint.setDescription(updatedRelayPoint.getDescription());}
        if (updatedRelayPoint.getOpenAtCloseAt()!= null){existingRelaypoint.setOpenAtCloseAt(updatedRelayPoint.getOpenAtCloseAt());}
        if (updatedRelayPoint.getUsername()!= null){existingRelaypoint.setPhoneNumber(updatedRelayPoint.getUsername());}
        if (updatedRelayPoint.getStatus()!= null){existingRelaypoint.setStatus(updatedRelayPoint.getStatus());}
        }

    private RelayPointMapperDTO relayPointDTOMapper(RelayPoint relayPoint){
        return new RelayPointMapperDTO(
                relayPoint.getName(),
                relayPoint.getCountry(),
                relayPoint.getCity(),
                relayPoint.getDistrict(),
                relayPoint.getPhoneNumber(),
                relayPoint.getEmail(),
                relayPoint.getAddress(),
                relayPoint.getDescription(),
                relayPoint.getOpenAtCloseAt(),
                relayPoint.getStatus()
        );
    }
}
