package com.chat.userservice.service.impl;


import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.dto.UserDto;
import com.chat.userservice.entity.SmsHistoryEntity;
import com.chat.userservice.entity.UserEntity;
import com.chat.userservice.enums.AppLang;
import com.chat.userservice.enums.SmsStatus;
import com.chat.userservice.enums.SmsType;
import com.chat.userservice.exps.SmsException;
import com.chat.userservice.repository.SmsHistoryRepository;
import com.chat.userservice.repository.UserRepository;
import com.chat.userservice.repository.sso.CandidateRepository;
import com.chat.userservice.service.ResourceBundleService;
import com.chat.userservice.service.ResourceBundleServiceImpl;
import com.chat.userservice.service.UserService;
import com.chat.userservice.service.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsHistoryServiceImpl {


    private final UserService userService;
    private final CandidateRepository candidateRepository;
    private final SmsHistoryRepository smsHistoryRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResourceBundleServiceImpl resourceBundleService;

    public void save(RegisterDto registerDto, String code, SmsType type) {
        var history = new SmsHistoryEntity();
        history.setPin(registerDto.getPin());
        history.setPhoneNumber(registerDto.getPhoneNumber());
        history.setSmsCode(code);
        history.setSmsType(type);
        history.setCreatedDate(LocalDateTime.now());
        history.setVisible(Boolean.TRUE);
        history.setStatus(SmsStatus.SEND);
        smsHistoryRepository.save(history);
    }


    public Map<String, Object> checkSmsCode(String pin, String code, HttpServletRequest request,
                                            HttpServletResponse response, AppLang lang) {

        SmsHistoryEntity lastSms = getLastSmsByPin(pin);
        String phoneNumber = lastSms.getPhoneNumber();
        if (lastSms == null) {
            log.warn("pin Incorrect! pin = {}, code = {}", pin, code);
            throw new SmsException(resourceBundleService.
                    getMessage("Sms.code.wrong", Locale.forLanguageTag(lang.name())));
        }
        if (lastSms.getCreatedDate().plusMinutes(2L).isBefore(LocalDateTime.now())) {
            log.warn("pin Incorrect! pin = {}, code = {}", pin, code);
            smsHistoryRepository.updateStatus(lastSms.getId(), SmsStatus.USED_WITH_TIMEOUT);
            throw new SmsException(resourceBundleService.
                    getMessage("Sms.verification.time.out", Locale.forLanguageTag(lang.name())));
        }
        if (!lastSms.getSmsCode().equals(code)) {
            throw new SmsException(resourceBundleService
                    .getMessage("Sms.code.wrong", Locale.forLanguageTag(lang.name())));
        }
        var candidate = candidateRepository.findByPinfl(pin);
        userService.createUsers(candidate, phoneNumber);

        String accessToken = jwtTokenProvider.createToken(lastSms.getPin(), request, true);
        String refreshToken = jwtTokenProvider.createToken(lastSms.getPin(), request, false);
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("token", accessToken);
        responseData.put("pin", lastSms.getPin());
        responseData.put("phoneNumber", lastSms.getPhoneNumber());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60L) // 1 kun
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        smsHistoryRepository.updateStatus(lastSms.getId(), SmsStatus.USED);
        return responseData;
    }

    public Long getSendLimitCountLast2Minute(String pin) {
        return smsHistoryRepository.countByPinAndCreatedDateLast2Between(
                pin,
                LocalDateTime.now().minusMinutes(2),
                LocalDateTime.now());
    }

    private SmsHistoryEntity getLastSmsByPin(String pin) {
        return smsHistoryRepository.findTopByPinAndVisibleOrderByCreatedDateDesc(pin, true);
    }

    public Map<String, Object> token(RegisterDto registerDto,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) {
        SmsHistoryEntity lastSms = getLastSmsByPin(registerDto.getPin());

        String accessToken = jwtTokenProvider.createToken(lastSms.getPin(), httpServletRequest, true);
        String refreshToken = jwtTokenProvider.createToken(lastSms.getPin(), httpServletRequest, false);
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("token", accessToken);
        responseData.put("pin", lastSms.getPin());
        responseData.put("phoneNumber", lastSms.getPhoneNumber());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60L) // 1 kun
                .build();

        httpServletResponse.addHeader("Set-Cookie", cookie.toString());
        return responseData;

    }
}
