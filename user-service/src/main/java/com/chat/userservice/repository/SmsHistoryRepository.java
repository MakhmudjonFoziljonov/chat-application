package com.chat.userservice.repository;

import com.chat.userservice.entity.SmsHistoryEntity;
import com.chat.userservice.enums.SmsStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {
    @Modifying
    @Query("UPDATE SmsHistoryEntity SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") String id, @Param("status") SmsStatus status);

    @Query("SELECT COUNT(e) FROM SmsHistoryEntity e " +
            "WHERE e.pin = :pin AND " +
            "e.createdDate BETWEEN :twoMinAgo AND :now")
    Long countByPinAndCreatedDateLast2Between(String pin, LocalDateTime localDateTime, LocalDateTime now);

    SmsHistoryEntity findTopByPinAndVisibleOrderByCreatedDateDesc(String pin, boolean b);
}
