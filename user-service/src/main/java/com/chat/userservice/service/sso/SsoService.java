package com.chat.userservice.service.sso;

import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.enums.AppLang;
import org.json.JSONObject;

import java.net.URISyntaxException;


public interface SsoService {
    JSONObject getFullData(RegisterDto registerDto, AppLang lang) throws URISyntaxException;
}






















