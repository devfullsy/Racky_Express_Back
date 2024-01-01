package com.sen_system.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileVerifications {
    /* public String extractTextFromPdf(InputStream pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(document);
            System.out.println("Extracted Text: " + pdfText);
            return pdfText;
        }
    }*/ //pour extraire du texte d'un dpf
    public LocalDate checkExpirationDate(String expirationDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(expirationDateStr, formatter);
    }

    public String createDir(String username, MultipartFile file, String path){
        try {
            File directory = new File(path +File.separator + username);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = Objects.requireNonNull(file.getOriginalFilename());
            Path targetLocation = Path.of(directory.getPath(), fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return  username + File.separator + fileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}