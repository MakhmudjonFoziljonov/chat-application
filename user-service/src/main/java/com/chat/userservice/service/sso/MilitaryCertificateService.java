package com.chat.userservice.service.sso;


import com.chat.userservice.entity.sso.MilitaryCertificateEntity;
import com.chat.userservice.repository.sso.MilitaryCertificateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class MilitaryCertificateService {
    private final MilitaryCertificateRepository militaryCertificateRepository;

    @Transactional
    public void create(JSONObject militaryCertificate, SimpleDateFormat sdf) throws ParseException {
        String beginDate = militaryCertificate.getString("beginDate");
        String endDate = militaryCertificate.getString("endDate");

        var militaryCertificateEntity = new MilitaryCertificateEntity();
        militaryCertificateEntity.setSeries(militaryCertificate.getString("series"));
        militaryCertificateEntity.setNumber(militaryCertificate.getString("number"));
        militaryCertificateEntity.setBeginDate(sdf.parse(beginDate));
        militaryCertificateEntity.setEndDate(sdf.parse(endDate));
        militaryCertificateRepository.save(militaryCertificateEntity);

    }
}
