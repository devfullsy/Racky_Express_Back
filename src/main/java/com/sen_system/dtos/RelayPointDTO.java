package com.sen_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelayPointDTO {
    private String name;
    private String country;
    private String city;
    private String district;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;
    private String openAtCloseAt;
    private String username;
    private MultipartFile idCard;
    private String registrationNumber;
    private String documentType;
    private LocalDate expirationDate;

}
