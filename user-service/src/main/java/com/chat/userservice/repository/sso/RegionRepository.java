package com.chat.userservice.repository.sso;

import com.chat.userservice.entity.sso.RegionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {
}
