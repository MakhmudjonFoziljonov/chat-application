package com.chat.userservice.repository.sso;

import com.chat.userservice.entity.sso.MilitaryDegreeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryDegreeRepository extends CrudRepository<MilitaryDegreeEntity, Long> {
}
