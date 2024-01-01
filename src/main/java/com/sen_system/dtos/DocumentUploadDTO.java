package com.sen_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUploadDTO {
        private String documentType;
        private String documentNumber;
        private LocalDate expirationDate;
        private MultipartFile file;
}
