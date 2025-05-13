package com.chat.userservice.service.impl;

import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.dto.SmsReSendDto;
import com.chat.userservice.dto.SmsVerificationDto;
import com.chat.userservice.dto.SmsVerificationResponseDto;
import com.chat.userservice.enums.AppLang;
import com.chat.userservice.enums.SmsType;
import com.chat.userservice.exps.SmsException;
import com.chat.userservice.repository.SmsHistoryRepository;
import com.chat.userservice.service.ResourceBundleServiceImpl;
import com.chat.userservice.service.SmsSenderService;
import com.chat.userservice.utils.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Locale;

import static com.chat.userservice.service.sso.SsoServiceImpl.getAuthToken;


@Slf4j
@Service
@RequiredArgsConstructor
public class SmsSenderServiceImpl implements SmsSenderService {
    @Value("${sso.url}")
    private String ssoUrl;

    @Value("${sso.clientId}")
    private String clientId;

    @Value("${sso.clientSecret}")
    private String clientSecret;

    @Value("${sms.text}")
    private String smsText;

    private static final int SMS_LIMIT_COUNT = 3;

    private final SmsHistoryRepository smsHistoryRepository;
    private final SmsHistoryServiceImpl smsHistoryService;
    private final ResourceBundleServiceImpl resourceBundleService;
    private final RestTemplate restTemplate;

    @Override
    public void sendRegistrationSms(RegisterDto registerDto,
                                    AppLang language) {

        SmsVerificationDto dto = new SmsVerificationDto();
        String code = RandomUtil.getCode();
        dto.setPinfl(registerDto.getPin());
        dto.setCode(code);
        dto.setPhone_number(registerDto.getPhoneNumber());
        dto.setExp_date(String.valueOf(LocalDateTime.now()));
        dto.setText(smsText + " " + code);

        String smsUrl = ssoUrl + "sso/send-sms";
        HttpHeaders headersData = new HttpHeaders();
        headersData.set("Content-Type", "application/json");
        headersData.set("Authorization", "Bearer " + getAuthToken(ssoUrl, clientId, clientSecret, restTemplate));

        HttpEntity<SmsVerificationDto> request = new HttpEntity<>(dto, headersData);
        SmsVerificationResponseDto response = restTemplate.postForObject(smsUrl, request, SmsVerificationResponseDto.class);

        assert response != null;
        if (Boolean.TRUE.equals(response.getSuccess())) {
            log.info("Sms send!");
            smsHistoryService.save(registerDto, dto.getCode(), SmsType.REGISTRATION);
        }

        if (smsHistoryService.getSendLimitCountLast2Minute(registerDto.getPin()) >= SMS_LIMIT_COUNT) {
            log.info("Sms limit reached pinfl {}", registerDto.getCode());
            throw new SmsException(resourceBundleService.getMessage("Sms.limit.reached", Locale.forLanguageTag(language.name())));
        }
    }

    @Override
    public SmsReSendDto reSendSmsCode(RegisterDto registerDto, AppLang lang) {
        var smsHistory = smsHistoryRepository.findTopByPinAndVisibleOrderByCreatedDateDesc(registerDto.getPin(), true);
        if (smsHistory == null) {
            throw new SmsException(resourceBundleService.getMessage("Sms.not.found", Locale.forLanguageTag(lang.name())));
        }
        String smsCode = RandomUtil.getCode();
        smsHistoryService.save(registerDto, smsCode, SmsType.RESEND_SMS);
        SmsReSendDto reSendDto = new SmsReSendDto();
        reSendDto.setCode(smsCode);
        return reSendDto;
    }
}
