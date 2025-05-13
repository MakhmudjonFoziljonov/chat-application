package com.chat.userservice.repository.sso;

import com.chat.userservice.entity.sso.StateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<StateEntity, Long> {
}
