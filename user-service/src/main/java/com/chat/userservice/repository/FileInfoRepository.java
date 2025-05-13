package com.chat.userservice.repository;

import com.chat.userservice.entity.sso.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends CrudRepository<FileEntity, Long> {
}
