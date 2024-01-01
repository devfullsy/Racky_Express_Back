package com.sen_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelayPointMapperDTO {
    private String name;
    private String country;
    private String city;
    private String district;
    private String phoneNumber;
    private String email;
    private String address;
    private String description;
    private String openAtCloseAt;
}
