package com.chat.userservice.service.sso;

import com.chat.userservice.entity.sso.StateEntity;
import com.chat.userservice.repository.sso.StateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;

    @Transactional
    public StateEntity create(JSONObject state) {
        StateEntity stateEntity = new StateEntity();
        stateEntity.setNameUz(state.getString("nameUz"));
        stateEntity.setNameRu(state.getString("nameCr"));
        stateEntity.setNameCr(state.getString("nameCr"));
        stateEntity.setNameQr(state.getString("nameQr"));
        stateEntity.setCode(state.getString("code"));
        stateEntity.setSoato(Long.parseLong(state.getString("coatoCode")));
        return stateRepository.save(stateEntity);
    }
}
