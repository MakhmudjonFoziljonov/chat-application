package com.chat.userservice.service;


import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.dto.SmsReSendDto;
import com.chat.userservice.enums.AppLang;

public interface SmsSenderService {
     void sendRegistrationSms(RegisterDto registerDto, AppLang language);

     SmsReSendDto reSendSmsCode(RegisterDto registerDto, AppLang lang);

}
