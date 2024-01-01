package com.sen_system.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RelayPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "relayPointName")
    private String name;

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @NotEmpty
    private String district;

    @NotEmpty
    private String phoneNumber;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String address;

    @NotEmpty
    private String description;

    @NotEmpty
    private String openAtCloseAt;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String idCard;//Rajouter la photo de la piece d'identité

    @NotEmpty
    private String documentType;

    @NotEmpty
    private LocalDate expirationDate;

    @NotEmpty
    private String registrationNumber;//numero SIRET - PATANTE - MATRICULE BOUTIQUE

}
