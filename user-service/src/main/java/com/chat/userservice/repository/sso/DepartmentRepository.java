package com.chat.userservice.repository.sso;

import com.chat.userservice.entity.sso.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long> {
}
