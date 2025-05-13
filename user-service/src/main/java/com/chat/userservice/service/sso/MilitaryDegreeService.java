package com.chat.userservice.service.sso;


import com.chat.userservice.entity.sso.MilitaryDegreeEntity;
import com.chat.userservice.repository.sso.MilitaryDegreeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilitaryDegreeService {
    private final MilitaryDegreeRepository militaryDegreeRepository;

    @Transactional
    public void create(JSONObject militaryDegree) {
        var militaryDegreeEntity = new MilitaryDegreeEntity();
        militaryDegreeEntity.setNameUz(militaryDegree.getString("nameUz"));
        militaryDegreeEntity.setNameRu(militaryDegree.getString("nameCr"));
        militaryDegreeEntity.setNameCr(militaryDegree.getString("nameCr"));
        militaryDegreeEntity.setNameQr(militaryDegree.getString("nameQr"));
        militaryDegreeRepository.save(militaryDegreeEntity);
    }
}
