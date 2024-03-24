package com.sen_system.dtos;

import lombok.Data;

@Data
public class ChangePwdRequest {

    private String username;

    private String newPassword;
}
