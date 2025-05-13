package com.chat.userservice.service.impl;

import com.chat.userservice.entity.UserEntity;
import com.chat.userservice.entity.sso.CandidateEntity;
import com.chat.userservice.repository.UserRepository;
import com.chat.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    @Override
    public UUID createUsers(CandidateEntity candidateEntity, String phoneNumber) {
        var user = new UserEntity();
        user.setFirstName(candidateEntity.getFirstName());
        user.setLastName(candidateEntity.getLastName());
        user.setParentName(candidateEntity.getParentName());
        user.setPassportSeries(candidateEntity.getPassportSeries());
        user.setPassportNumber(candidateEntity.getPassportNumber());
        user.setUsername("");
        user.setUniqueId(UUID.randomUUID());
        user.setPin(candidateEntity.getPinfl());
        user.setPhoneNumber(phoneNumber);
        user.setUserDevice(System.getProperty("os.name")
                + " " + System.getProperty("os.version")
                + " " + System.getProperty("os.arch"));
        user.setIsActive(true);
        user.setLastLogin(new Date());
        userRepo.save(user);
        return user.getUniqueId();
    }
}
