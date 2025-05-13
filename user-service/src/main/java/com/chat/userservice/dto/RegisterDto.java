package com.chat.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String pin;
    private String phoneNumber;
    private String code;
    private String username;
}
