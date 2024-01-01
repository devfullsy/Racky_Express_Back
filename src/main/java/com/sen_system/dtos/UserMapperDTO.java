package com.sen_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMapperDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private String compagny;
    private String country;
    private boolean enabled;


}
