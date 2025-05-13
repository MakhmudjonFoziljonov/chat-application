package com.chat.userservice.service.sso;


import com.chat.userservice.entity.sso.RegionEntity;
import com.chat.userservice.repository.sso.RegionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository repository;

    @Transactional
    public RegionEntity create(JSONObject region) {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setNameUz(region.getString("nameUz"));
        regionEntity.setNameCr(region.getString("nameCr"));
        regionEntity.setNameRu(region.getString("nameCr"));
        regionEntity.setNameQr(region.getString("nameQr"));
        regionEntity.setCode(region.getString("code"));
        regionEntity.setSoato(Long.parseLong(region.getString("coatoCode")));
        return repository.save(regionEntity);
    }
}
