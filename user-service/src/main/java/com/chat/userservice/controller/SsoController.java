package com.chat.userservice.controller;

import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.enums.AppLang;
import com.chat.userservice.service.sso.SsoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v.1/sso/")
@RequiredArgsConstructor
public class SsoController {
    private final SsoServiceImpl ssoService;

    @PostMapping("full-data-v2")
    public JSONObject fullData(@RequestBody RegisterDto registerDto,
                               @RequestHeader(name = "Accept-Language",
                                       defaultValue = "uz") AppLang lang) throws URISyntaxException {
        return ssoService.getFullData(registerDto, lang);
    }
}
