package com.chat.userservice.repository.sso;

import com.chat.userservice.entity.sso.CandidateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<CandidateEntity, Long> {

    CandidateEntity findByPinfl(String pin);
}
