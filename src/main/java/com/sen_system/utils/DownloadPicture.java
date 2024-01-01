package com.sen_system.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Service
@RequiredArgsConstructor
public class DownloadPicture {
    public String initialProfilPicture(String subfolderName, String dest) {
        Path sourcePath = Paths.get("C:\\Users\\HP\\OneDrive\\Bureau\\photoProfileApplicationRackyExpress\\profilinitial.png");
        Path destinationPath = Paths.get(dest+"\\"+ subfolderName +"\\profilinitial.png");

        try {
            Files.createDirectories(destinationPath.getParent());
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Une erreur s'est produite lors de la copie de la photo.");
        }

        return subfolderName+"\\profilinitial.png";
    }
}
