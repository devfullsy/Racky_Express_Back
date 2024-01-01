package com.sen_system.dtos;

import lombok.Data;

@Data
public class SignUpRequest {


    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String country;
}
