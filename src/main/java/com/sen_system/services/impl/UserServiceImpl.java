package com.sen_system.services.impl;

import com.sen_system.dtos.UserDTO;
import com.sen_system.entities.Clients;
import com.sen_system.repositories.UserRepository;
import com.sen_system.services.UserService;
import com.sen_system.utils.FileVerifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileVerifications fileVerifications;

    @Value("${profil.upload.directory}")
    private String uploadDirectory;
    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            @Override
            public UserDetails loadUserByUsername(String username){
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("l'utilisateur n'existe pas"));
            }
        };
    }

    public ResponseEntity<UserDTO> userInfo(String username){
        Optional<Clients> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            Clients user = optionalUser.get();
            byte[] profileImageBytes = null;

            try {
                profileImageBytes = getUserProfileImage(username);
            } catch (IOException e) {
                e.printStackTrace();
            }

            UserDTO userDTO = mapUserToDTO(user);
            userDTO.setProfileImage(profileImageBytes);

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    public void updateUser(String username, Clients updatedUser){
        userRepository.findByUsername(username)
                .map(existingUser -> {
                    check(updatedUser, existingUser);
                    return ResponseEntity.ok(userRepository.save(existingUser));
                });
    }


    @Override
    public String saveUserProfileImage(String username, MultipartFile file) throws IOException {

        Optional<Clients> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            Clients user = optionalUser.get();
            user.setProfileImage(fileVerifications.createDir(username,file,uploadDirectory));
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException("Utilisateur non trouvé ");
        }

        return "Votre photo de profile a bien été mise à jour";
    }


    public byte[] getUserProfileImage(String username) throws IOException {
        Optional<Clients> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            Clients user = optionalUser.get();
            String imagePath = user.getProfileImage();

            if (imagePath != null && !imagePath.isEmpty()) {
                Path filePath = Path.of(uploadDirectory, imagePath);
                return Files.readAllBytes(filePath);
            }
            return null;
        } else {

            throw new EntityNotFoundException("Utilisateur non trouvé");
        }
    }

    public static void check(@RequestBody @Validated Clients updatedUser, Clients existingUser) {
        if (updatedUser.getUsername()!= null){existingUser.setUsername(updatedUser.getUsername());}
        if (updatedUser.getEmail()!= null){existingUser.setEmail(updatedUser.getEmail());}
        if (updatedUser.getFirstName()!= null){existingUser.setFirstName(updatedUser.getFirstName());}
        if (updatedUser.getLastName()!= null){existingUser.setLastName(updatedUser.getLastName());}
        if (updatedUser.getPhoneNumber()!= null){existingUser.setPhoneNumber(updatedUser.getPhoneNumber());}
        if (updatedUser.getCountry()!= null){existingUser.setCountry(updatedUser.getCountry());}
        if (updatedUser.getCompagny()!= null){existingUser.setCompagny(updatedUser.getCompagny());}
    }


    private UserDTO mapUserToDTO(Clients user) {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getCountry(),
                user.getProfileImage().getBytes(),
                user.getCompagny()
        );
    }
}
