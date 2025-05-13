package com.chat.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsVerificationDto {
    private String pinfl;
    private String phone_number;
    private String code;
    private String exp_date;
    private String text;
}
