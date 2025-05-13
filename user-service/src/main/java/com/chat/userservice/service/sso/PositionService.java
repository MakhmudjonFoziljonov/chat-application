package com.chat.userservice.service.sso;


import com.chat.userservice.entity.sso.PositionEntity;
import com.chat.userservice.repository.sso.PositionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    @Transactional
    public void create(JSONObject positionEntity) {
        var position = new PositionEntity();
        position.setNameUz(positionEntity.getString("nameUz"));
        position.setNameRu(positionEntity.getString("nameCr"));
        position.setNameCr(positionEntity.getString("nameCr"));
        position.setNameQr(positionEntity.getString("nameQr"));
        position.setCode(positionEntity.getString("code"));
        positionRepository.save(position);
    }
}
