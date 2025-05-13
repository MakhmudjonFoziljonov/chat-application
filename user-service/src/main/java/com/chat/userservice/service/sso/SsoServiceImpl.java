package com.chat.userservice.service.sso;

import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.dto.sso.LoginDto;
import com.chat.userservice.dto.sso.RequestDto;
import com.chat.userservice.dto.sso.ResponseDto;
import com.chat.userservice.enums.AppLang;
import com.chat.userservice.exps.RecordNotFoundException;
import com.chat.userservice.service.ResourceBundleServiceImpl;
import com.chat.userservice.service.impl.SmsSenderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SsoServiceImpl implements SsoService {

    private final RestTemplate restTemplate;
    private final ResourceBundleServiceImpl resourceBundleService;
    private final CandidateService candidateService;
    private final SmsSenderServiceImpl smsSenderService;

    @Value("${sso.url}")
    private String mainUrl;

    @Value("${sso.clientId}")
    private String clientId;

    @Value("${sso.clientSecret}")
    private String clientSecret;

    private static final String USERNAME = "E-n0Mzo@";
    private static final String PASSWORD = "2024E-n0Mzo@2024";



    @Override
    public JSONObject getFullData(RegisterDto registerDto, AppLang lang) throws URISyntaxException {
        String urlForData = mainUrl + "ekadr/get-necessary-personal-data";
        URI uri = new URI(urlForData);
        HttpEntity<Map<String, Object>> requestEntity = getMapHttpEntity(registerDto.getPin());
        try {
            ResponseEntity<Map<String, Object>> responseData =
                    restTemplate.exchange(uri, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
                    });
            if (responseData.getStatusCode().value() == 200 && responseData.getBody() != null) {
                JSONObject jsonObject = getJsonObject(responseData, lang);
                candidateService.create(jsonObject, registerDto, lang);
                smsSenderService.sendRegistrationSms(registerDto, lang);
                return jsonObject;
            }
            throw new RecordNotFoundException(resourceBundleService.getMessage("Nothing.found", Locale.forLanguageTag(lang.name())));
        } catch (Exception e) {
            log.error("Hodim ma'lumotlari topilmadi {}", e.getMessage());
            throw new RecordNotFoundException(resourceBundleService.getMessage("Employee.information.not.found", Locale.forLanguageTag(lang.name())));
        }
    }

    private HttpEntity<Map<String, Object>> getMapHttpEntity(String pinfl) {
        HttpHeaders headersData = new HttpHeaders();
        headersData.set("Content-Type", "application/json");
        headersData.set("Authorization", "Bearer " + getAuthToken(mainUrl, clientId, clientSecret, restTemplate));

        Map<String, Object> body = new HashMap<>();
        body.put("jshshir", pinfl);
        body.put("withBirthAddress", true);
        body.put("withGenderMarriage", false);
        body.put("withPassport", true);
        body.put("withPassportPhoto", false);
        body.put("withNationality", false);
        body.put("withCitizenship", false);
        body.put("withEducation", false);
        body.put("withOrganization", true);
        body.put("withDepartment", true);
        body.put("withPosition", true);
        body.put("withMilitaryDegree", true);
        body.put("withMilitaryPhoto", true);
        body.put("withMilitaryCertificate", true);
        return new HttpEntity<>(body, headersData);
    }


    public static String getAuthToken(String mainUrl, String clientId, String clientSecret, RestTemplate restTemplate) {
        String urlLogin = mainUrl + "auth/token";

        RequestDto requestDto = new RequestDto();
        requestDto.setGrant_type("password_system");
        requestDto.setClient_id(clientId);
        requestDto.setClient_secret(clientSecret);
        requestDto.setRedirect_uri("redirect_uri");
        requestDto.setUsername(USERNAME);
        requestDto.setPassword(PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<RequestDto> entity1 = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ResponseDto> response1 = restTemplate.postForEntity(urlLogin, entity1, ResponseDto.class);

        if (response1.getBody() == null) throw new RecordNotFoundException("Ssodan token olishda xatolik!");
        ResponseDto responseDto1 = response1.getBody();
        if (responseDto1.getResult() == null) throw new RecordNotFoundException("Ssodan token ma'lumotlari kelmadi!");
        LoginDto ssoLoginDto = responseDto1.getResult();
        String token = ssoLoginDto.getAccess_token();

        log.info("token: {}", token);

        if (token == null || token.isEmpty()) throw new RecordNotFoundException("Ssodan token kelmadi!");

        return token;
    }

    private JSONObject getJsonObject(ResponseEntity<Map<String, Object>> responseData, AppLang lang) {
        JSONObject object = new JSONObject(responseData.getBody());
        if (object.isEmpty())
            throw new RecordNotFoundException(resourceBundleService.getMessage("Data.is.empty", Locale.forLanguageTag(lang.name())));

        String result = "result";
        if (!object.getBoolean("success") || !object.has(result) || !(object.get(result) instanceof JSONObject))
            throw new RecordNotFoundException(resourceBundleService.getMessage("Info.not.found", Locale.forLanguageTag(lang.name())));

        JSONObject resultJson = object.getJSONObject(result);
        if (resultJson == null || resultJson.isEmpty()) throw new RecordNotFoundException("Data is incomplete!");
        return resultJson;
    }
}