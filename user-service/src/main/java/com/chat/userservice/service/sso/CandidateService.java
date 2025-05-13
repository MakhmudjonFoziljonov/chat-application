package com.chat.userservice.service.sso;

import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.enums.AppLang;
import org.json.JSONObject;

import java.text.ParseException;

public interface CandidateService {
     void create(JSONObject jsonObject, RegisterDto registerDto, AppLang lang) throws ParseException;
}
