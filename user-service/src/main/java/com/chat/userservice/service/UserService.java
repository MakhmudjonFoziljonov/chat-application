package com.chat.userservice.service;

import com.chat.userservice.dto.RegisterDto;
import com.chat.userservice.entity.sso.CandidateEntity;

import java.util.UUID;

public interface UserService {
     UUID createUsers(CandidateEntity candidateEntity, String phoneNumber);
}
