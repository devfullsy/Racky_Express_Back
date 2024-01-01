package com.sen_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriversDTO {
    private String username;
    private MultipartFile licence;
    private MultipartFile idCard;
    private LocalDate idCardExpirationDate;

}
