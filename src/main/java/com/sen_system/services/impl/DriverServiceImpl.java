package com.sen_system.services.impl;

import com.sen_system.dtos.DriversDTO;
import com.sen_system.dtos.DriversMapperDTO;
import com.sen_system.entities.Clients;
import com.sen_system.entities.Drivers;
import com.sen_system.entities.Role;
import com.sen_system.repositories.DriverRepository;
import com.sen_system.repositories.UserRepository;
import com.sen_system.services.DriversService;
import com.sen_system.utils.FileVerifications;
import com.sen_system.utils.ReferenceGeneretor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriversService {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final FileVerifications fileVerifications;
    private final ReferenceGeneretor referenceGeneretor;
    @Value("${doc.upload.directory}")
    private String docTransporteur;
    @Override
    public String createDriver(DriversDTO driversDTO) {
        Optional<Clients> userOptional = userRepository.findByUsername(driversDTO.getUsername());
        if(userOptional.isPresent()){
            if (driverRepository.findDriversByUsername(driversDTO.getUsername()).isEmpty()){
                Drivers drivers = new Drivers();
                Clients user = userOptional.get();
                String ref = referenceGeneretor.generetedRef(user.getCountry(),user.getFirstName(),user.getLastName());
                if(driversDTO.getUsername()!= null) {drivers.setUsername(driversDTO.getUsername());}
                if(driversDTO.getIdCardExpirationDate()!= null){drivers.setIdCardExpirationDate(fileVerifications.checkExpirationDate(String.valueOf(driversDTO.getIdCardExpirationDate())));}
                if(driversDTO.getLicence()!= null){ drivers.setLicence(fileVerifications.createDir(driversDTO.getUsername(),driversDTO.getLicence(),docTransporteur));}
                if(driversDTO.getIdCard()!= null){drivers.setIdCard(fileVerifications.createDir(driversDTO.getUsername(),driversDTO.getIdCard(),docTransporteur));}
                drivers.setReference(ref);
                user.setRole(Role.TRANSPORTEUR);
                driverRepository.save(drivers);

                return "Le conducteur a bien été crée";
            }
            return "Un chauffeur avec ce nom d'utilisateur existe déjà";
        }
        return "Le chauffeur n'a pas encore crée son compte";
    } // ADMIN

    @Override
    public List<Drivers> getAllDrivers() {
        return driverRepository.findAll();
    } // SUPADMIN

    @Override
    public ResponseEntity<DriversMapperDTO> getDriverByReference(String reference) {
        return driverRepository.findDriversByReference(reference)
                .map(drivers -> ResponseEntity.ok(driverMapperDTO(drivers)))
                .orElse(ResponseEntity.notFound().build());
    } // POINT RELAIS AND ADMIN

    @Override
    public ResponseEntity<DriversMapperDTO> getDriverByUsername(String username) {
        return driverRepository.findDriversByUsername(username)
                .map(drivers -> ResponseEntity.ok(driverMapperDTO(drivers)))
                .orElse(ResponseEntity.notFound().build());
    }// CONDUCTEUR

    @Override
    public ResponseEntity<String> updateDriver(String reference, DriversDTO updatedDriver) {
        return driverRepository.findDriversByReference(reference)
                .map(existingDrivers -> {
                        checkDrive(updatedDriver,existingDrivers);
                        driverRepository.save(existingDrivers);
                        return ResponseEntity.ok("les informations du conducteur ont été modifié avec succès.");
        })
            .orElse(ResponseEntity.notFound().build());
    } //ADMIN
    @Override
    public ResponseEntity<String> deleteDriver(String reference) {
        Optional<Drivers> optionalDrivers = driverRepository.findDriversByReference(reference);
        optionalDrivers.ifPresent(driverRepository::delete);
        return ResponseEntity.ok("Le conducteur a bien été supprimé");
    } // SUPADMIN

    public void checkDrive(DriversDTO updatedDriver, Drivers existingDrivers) {
        if(updatedDriver.getIdCardExpirationDate()!= null){existingDrivers.setIdCardExpirationDate(fileVerifications.checkExpirationDate(String.valueOf(updatedDriver.getIdCardExpirationDate())));}
        if(updatedDriver.getIdCard()!= null){existingDrivers.setIdCard(fileVerifications.createDir(updatedDriver.getUsername(),updatedDriver.getIdCard(),docTransporteur));}
        if(updatedDriver.getLicence()!= null){ existingDrivers.setLicence(fileVerifications.createDir(updatedDriver.getUsername(),updatedDriver.getLicence(),docTransporteur));}
    }
    private DriversMapperDTO driverMapperDTO(Drivers drivers){
        if(drivers.getUsername()!= null){
            Optional<Clients> userOptional = userRepository.findByUsername(drivers.getUsername());
            Clients clients = userOptional.get();
        return new DriversMapperDTO(
                drivers.getReference(),
                clients.getFirstName(),
                clients.getLastName(),
                clients.getEmail(),
                drivers.getUsername(),
                clients.getPhoneNumber());
        }
        return null;
    }
}
