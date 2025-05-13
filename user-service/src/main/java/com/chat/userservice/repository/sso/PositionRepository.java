package com.chat.userservice.repository.sso;

import com.chat.userservice.entity.sso.PositionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<PositionEntity, Long> {
}
