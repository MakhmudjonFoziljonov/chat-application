package com.chat.userservice.repository.sso;

import com.chat.userservice.entity.sso.MilitaryCertificateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryCertificateRepository extends CrudRepository<MilitaryCertificateEntity, Long> {
}
